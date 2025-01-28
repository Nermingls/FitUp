package dev.nermingules.nsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.nermingules.nsapp.databinding.ItemBodyPartBinding
import dev.nermingules.nsapp.ui.exerciseDetail.ExerciseListViewModel
import dev.nermingules.nsapp.ui.exerciseDetail.targetBodyPart


class TargetAdapter(
    private val targets: List<BodyTarget>,
) : RecyclerView.Adapter<TargetAdapter.BodyTargetViewHolder>() {

    class BodyTargetViewHolder(val binding: ItemBodyPartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyTargetViewHolder {
        val binding = ItemBodyPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BodyTargetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BodyTargetViewHolder, position: Int) {
        val target = targets[position]

        // Bind data
        holder.binding.title.text = target.name
        // Pass the data to the detail screen

        // Item click event
        holder.itemView.setOnClickListener {
            targetBodyPart =target.name
                Navigation.findNavController(holder.itemView).navigate(HomePageFragmentDirections.openExercise(target.name))
        }
    }

    override fun getItemCount() = targets.size
}
