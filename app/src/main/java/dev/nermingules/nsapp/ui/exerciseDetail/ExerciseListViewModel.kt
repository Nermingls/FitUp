package dev.nermingules.nsapp.ui.exerciseDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

data class ExercisesBodyPart(
    val id: String,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val target: String
)
var targetBodyPart: String = ""

class ExerciseListViewModel : ViewModel() {


    fun getExercisesBodyPart() = liveData(Dispatchers.IO) {
        val exercisesBodyPart = fetchExercises()
        emit(exercisesBodyPart)
    }

    private fun fetchExercises(): List<ExercisesBodyPart> {
        val client = OkHttpClient()
        val gson = Gson()

        val request = Request.Builder()
            .url("https://exercisedb.p.rapidapi.com/exercises/target/$targetBodyPart?limit=10")
            .get()
            .addHeader("X-RapidAPI-Key", "32f8b095d6mshdffff9e272a578ep1077d8jsnaa75fc120fa2")
            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                val listType = object : TypeToken<List<ExercisesBodyPart>>() {}.type
                gson.fromJson(responseBody, listType)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
