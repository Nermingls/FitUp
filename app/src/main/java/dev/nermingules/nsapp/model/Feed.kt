package dev.nermingules.nsapp.model

data class Feed(
    val userNickName: String,
    val localName: String,
    val userAvatar: String,
    val imageUrl: String,
    val description: String,
    val postedAgo: String
)