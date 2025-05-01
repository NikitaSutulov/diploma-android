package com.nikitasutulov.macsro.data.ui

data class Group(
    val gid: String,
    val name: String,
    val members: List<GroupMember>
)
