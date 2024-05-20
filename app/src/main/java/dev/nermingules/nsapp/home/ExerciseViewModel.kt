package dev.nermingules.nsapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ExerciseViewModel : ViewModel() {

    fun getExercises() = liveData(Dispatchers.IO) {
        val exercises = fetchExercises()
        emit(exercises)
    }

    private fun fetchExercises(): List<Exercise> {
        val client = OkHttpClient()
        val gson = Gson()

        val request = Request.Builder()
            .url("https://exercisedb.p.rapidapi.com/exercises/bodyPart/back?limit=10")
            .get()
            .addHeader("X-RapidAPI-Key", "32f8b095d6mshdffff9e272a578ep1077d8jsnaa75fc120fa2")
            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                val listType = object : TypeToken<List<Exercise>>() {}.type
                gson.fromJson(responseBody, listType)
            } else {
                // HTTP isteği başarısız oldu veya yanıt alınamadıysa, boş bir liste döndürün.
                emptyList()
            }
        } catch (e: Exception) {
            // Hata durumunu kullanıcıya bildirin ve loglayın.
            e.printStackTrace()
            // Hata durumunda kullanıcıya bildirim sağlamak için boş bir liste döndürün.
            emptyList()
        }
    }

}
