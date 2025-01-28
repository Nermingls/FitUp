package dev.nermingules.nsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

//@Entity(tableName = "doctors")
data class Doctor(
//    @PrimaryKey(autoGenerate = true)
//    val id: UUID,
    val name: String,
    val imgUrl:String,
    val hospital: String,
    val medicalSpecialty: String,
    val comment: String,
)