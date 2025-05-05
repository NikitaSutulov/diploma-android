package com.nikitasutulov.macsro.data.ui

data class VolunteerRating(
    val gid: String,
    val name: String,
    val ratingNumber: Int,
    val position: Int,
    val isOfCurrentUser: Boolean
)
