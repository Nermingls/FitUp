package dev.nermingules.nsapp.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2
import dev.nermingules.nsapp.database.dao.MealDAO
import dev.nermingules.nsapp.database.dao.MedicineDAO
import dev.nermingules.nsapp.database.dao.PostDao
import dev.nermingules.nsapp.model.Meal
import dev.nermingules.nsapp.model.Medicine
import dev.nermingules.nsapp.model.Post

@Database(entities = [Medicine::class, Meal::class, ], version = 1, exportSchema = false)
abstract class RDatabase : RoomDatabase() {
    abstract fun medicinesDao(): MedicineDAO
    abstract fun mealDao(): MealDAO
//    abstract fun postDao(): PostDao
//    abstract fun doctorsDao(): DoctorDao



    // Singleton
    companion object {

        @Volatile
        private var instance: RDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        @SuppressLint("RestrictedApi")
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RDatabase::class.java,
            "Database"
        ).addMigrations(MIGRATION_1_2)  // Add migration if schema changes
            .build()
    }
}