package dev.nermingules.nsapp.ui.meal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dev.nermingules.nsapp.base.BaseViewModel
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.model.Meal
import kotlinx.coroutines.launch

class MealsViewModel(application: Application) : BaseViewModel(application) {

    val meals = MutableLiveData<List<Meal>>()

    fun getDataFromSQLite() {
        launch {
            val mealList = RDatabase(getApplication()).mealDao().getAllMeal()
            showMeals(mealList)
        }
    }

    private fun showMeals(mealList: List<Meal>) {
        meals.value = mealList

    }
}