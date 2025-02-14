package  dev.nermingules.nsapp.adapter.mealCardAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.nermingules.nsapp.databinding.MealRecyclerRowBinding
import dev.nermingules.nsapp.model.Meal

class MealAdapter(
    private val meals: List<Meal>,
) : RecyclerView.Adapter<MealHolder>(

) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val binding =
            MealRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealHolder(binding)
    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        holder.bindMeal(meals[position])
    }

    override fun getItemCount(): Int = meals.size

}