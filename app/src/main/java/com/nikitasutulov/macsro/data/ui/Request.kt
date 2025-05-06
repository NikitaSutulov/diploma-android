package com.nikitasutulov.macsro.data.ui

data class Request(
    val gid: String,
    val from: String,
    val to: String,
    val text: String,
    val isRead: Boolean,
    val canBeRead: Boolean
)
