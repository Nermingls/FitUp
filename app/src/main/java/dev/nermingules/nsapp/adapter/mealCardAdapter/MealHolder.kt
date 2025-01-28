package  dev.nermingules.nsapp.adapter.mealCardAdapter

import androidx.recyclerview.widget.RecyclerView
import dev.nermingules.nsapp.databinding.MealRecyclerRowBinding
import dev.nermingules.nsapp.model.Meal

class MealHolder(val binding: MealRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindMeal(meal: Meal) {
        binding.mealText.text = meal.name
        binding.mealTimeText.text = meal.time
    }
}