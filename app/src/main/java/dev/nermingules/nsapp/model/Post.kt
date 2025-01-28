package dev.nermingules.nsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val downloadUrl: String,
    val userEmail: String,
    val comment: String,
    val date: String // Timestamp in ISO8601 or other formats
)