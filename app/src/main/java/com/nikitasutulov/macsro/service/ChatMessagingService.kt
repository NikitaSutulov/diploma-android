package com.nikitasutulov.macsro.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nikitasutulov.macsro.R
import org.json.JSONObject
import java.util.Random

class ChatMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val messageData = JSONObject(remoteMessage.notification?.body!!)

        val senderGID = messageData.getString("senderGID")
        val roomId = messageData.getString("roomId")
        val currentUserId = getCurrentUserId()
        val currentOpenRoomId = getCurrentOpenRoomId()

        if (senderGID == currentUserId) {
            Log.d("FCM", "Ignoring message from self")
            return
        }

        if (roomId == currentOpenRoomId) {
            Log.d("FCM", "Ignoring message from open chat room")
            return
        }

        Log.d("FCM", "Message received: ${remoteMessage.notification?.body}")
        val title = remoteMessage.notification?.title
        val text = messageData.getString("text")
        showNotification(title, text)
    }

    private fun showNotification(title: String?, message: String?) {
        val channelId = "chat_notifications"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Chat Messages", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title ?: "New message")
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_chat_24)
            .build()

        manager.notify(Random().nextInt(), notification)
    }

    private fun getCurrentOpenRoomId(): String? {
        return getSharedPreferences("chat", MODE_PRIVATE).getString("currentRoomId", null)
    }

    private fun getCurrentUserId(): String? {
        return getSharedPreferences("user_session", MODE_PRIVATE).getString("currentUserId", null)
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "FCM token: $token")
    }
}
