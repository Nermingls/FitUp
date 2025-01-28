package dev.nermingules.nsapp.meal

import android.app.Application
import dev.nermingules.nsapp.base.BaseViewModel
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.model.Meal
import kotlinx.coroutines.launch

class MealAddViewModel(application: Application) : BaseViewModel(application) {

    fun storeInSQLite(meal: Meal) {
        launch {
            val dao = RDatabase(getApplication()).mealDao()
            dao.insertMeal(meal)
        }
    }

}