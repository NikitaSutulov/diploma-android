package com.nikitasutulov.macsro.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class SessionManager private constructor(context: Context) {
    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_EXPIRATION = "expiration"

        @Volatile
        private var INSTANCE: SessionManager? = null
        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val sharedPref: SharedPreferences = context.applicationContext
        .getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveToken(token: String, expiration: String) {
        with(sharedPref.edit()) {
            putString(KEY_TOKEN, token)
            putString(KEY_EXPIRATION, expiration)
            apply()
        }
    }

    fun getToken(): String? = sharedPref.getString(KEY_TOKEN, null)

    fun getExpiration(): String? = sharedPref.getString(KEY_EXPIRATION, null)

    fun clearSession() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

    fun isLoggedIn(): Boolean = !getToken().isNullOrBlank() && !isTokenExpired()

    private fun isTokenExpired(): Boolean {
        val expirationString = getExpiration() ?: return true
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            val expirationDate = dateFormat.parse(expirationString)
            val currentDate = Date()
            expirationDate?.before(currentDate) ?: true
        } catch (e: ParseException) {
            true
        }
    }
}
