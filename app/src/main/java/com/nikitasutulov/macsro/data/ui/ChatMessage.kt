package com.nikitasutulov.macsro.data.ui

import com.google.firebase.Timestamp

data class ChatMessage(
    val senderGID: String = "",
    val senderUsername: String = "",
    val text: String = "",
    val timestamp: Timestamp? = null
)
