package com.nikitasutulov.macsro.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.ErrorResponse
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventPaginationQueryDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.utils.SessionManager
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.*

class EventCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val prefs = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)
    private val sessionManager = SessionManager.getInstance(context)

    override suspend fun doWork(): Result {
        val token = sessionManager.getToken()
        val isSendGeoNotifications = sessionManager.getIsSendGeoNotifications()
        val maxDistance = sessionManager.getMaxDistance()
        val eventStatusGID = inputData.getString("eventStatusGID") ?: return Result.failure()
        val volunteerGID = inputData.getString("volunteerGID") ?: return Result.failure()

        val lastUpdateStr = prefs.getString("last_update_time", null)
        val lastUpdate = lastUpdateStr?.let { parseDate(it) } ?: Date().also {
            prefs.edit {
                putString("last_update_time", formatDate(it))
                apply()
            }
            return Result.success()
        }

        val location = getCurrentLocation(applicationContext)
        val userLat = location?.latitude
        val userLon = location?.longitude

        val volunteersDistrictsApi = RetrofitClient.volunteersDistrictsApi
        val eventApi = RetrofitClient.eventApi

        val volunteersDistrictsGIDs: List<String>
        val newEvents: Set<EventDto>

        val volunteersDistrictsResponse = performRequest {
            volunteersDistrictsApi.getByVolunteerGID(
                "Bearer $token",
                volunteerGID
            )
        }
        if (volunteersDistrictsResponse is BaseResponse.Error) {
            return Result.failure()
        }
        volunteersDistrictsGIDs = (volunteersDistrictsResponse as BaseResponse.Success).data!!.map { it.districtGID }
        val eventsResponse = performRequest {
            eventApi.getSorted(
                "Bearer $token",
                EventPaginationQueryDto(eventStatusGID)
            )
        }
        if (eventsResponse is BaseResponse.Error) {
            return Result.failure()
        }
        newEvents = (eventsResponse as BaseResponse.Success).data!!.items.filter { parseDate(it.createdAt).after(lastUpdate) }.toSet()
        val newEventsInChosenDistricts = newEvents.filter { it.districtGID in volunteersDistrictsGIDs }.toSet()
        val newEventsNearUser = if (isSendGeoNotifications) {
            newEvents.filter { isNearUser(it, userLat!!, userLon!!, maxDistance) }.toSet()
        } else {
            setOf()
        }
        val eventsToNotifyAbout = newEventsInChosenDistricts + newEventsNearUser

        if (eventsToNotifyAbout.isNotEmpty()) {
            Log.d("WORK_MANAGER", "Sending notification...")
            sendNotification(applicationContext, eventsToNotifyAbout.size)
            prefs.edit {
                putString("last_update_time", formatDate(Date()))
                apply()
            }
            Log.d("WORK_MANAGER", "The notification must have been sent")
        }

        return Result.success()
    }

    private fun parseDate(dateString: String): Date {
        return Date.from(Instant.parse(dateString))
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(date)
    }

    private fun sendNotification(context: Context, count: Int) {
        val channelId = "event_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(channelId, "Event Updates", NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(context.getString(R.string.new_events))
            .setContentText(context.getString(R.string.new_events_available, count))
            .build()

        manager.notify(1, notification)
    }

    private suspend fun getCurrentLocation(context: Context): Location? = suspendCoroutine { cont ->
        val fused = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cont.resume(null)
            return@suspendCoroutine
        }
        fused.lastLocation.addOnSuccessListener {
            cont.resume(it)
        }.addOnFailureListener {
            cont.resume(null)
        }
    }

    private fun isNearUser(event: EventDto, userLatitude: Double, userLongitude: Double, maxDistanceKm: Int): Boolean {
        val earthRadiusKm = 6371.0

        val latitudeDifference = Math.toRadians(event.latitude - userLatitude)
        val longitudeDifference = Math.toRadians(event.longitude - userLongitude)

        val userLatitudeRad = Math.toRadians(userLatitude)
        val eventLatitudeRad = Math.toRadians(event.latitude)

        val haversine = sin(latitudeDifference / 2).pow(2) +
                cos(userLatitudeRad) * cos(eventLatitudeRad) *
                sin(longitudeDifference / 2).pow(2)

        val angularDistance = 2 * atan2(sqrt(haversine), sqrt(1 - haversine))
        val actualDistanceKm = earthRadiusKm * angularDistance

        return actualDistanceKm <= maxDistanceKm
    }

    suspend fun <T> performRequest(
        request: suspend () -> retrofit2.Response<T>
    ): BaseResponse<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                BaseResponse.Success(
                    data = response.body(),
                    code = response.code()
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    JSONObject(errorBody ?: "").getString("message")
                } catch (e: Exception) {
                    "Unknown error occurred"
                }
                BaseResponse.Error(ErrorResponse(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("WorkerRequest", "Exception handled: ${e.stackTraceToString()}")
            val message = when (e) {
                is java.net.ConnectException -> "Failed to connect to the server"
                is retrofit2.HttpException -> "Server error: ${e.message}"
                else -> "Unexpected error: ${e.localizedMessage}"
            }
            BaseResponse.Error(ErrorResponse(message))
        }
    }
}
