//package dev.nermingules.nsapp.database.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import dev.nermingules.nsapp.model.Doctor
//import dev.nermingules.nsapp.model.Post
//
//@Dao
//interface DoctorDao {
//    @Insert
//    suspend fun insertDoctor(doctor: Doctor)
//
//    @Query("SELECT * FROM doctors")
//    suspend fun getAllDoctors(): List<Doctor>
//}
