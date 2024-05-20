package dev.nermingules.nsapp.exerciseDetail
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dev.nermingules.nsapp.base.BaseFragment
import dev.nermingules.nsapp.databinding.FragmentExerciseBinding

class ExerciseFragment  :  BaseFragment<FragmentExerciseBinding>(FragmentExerciseBinding::inflate) {

    private val viewModel: ExerciseListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.getExercisesBodyPart().observe(viewLifecycleOwner, Observer { exercisesBodyPart ->
            if (exercisesBodyPart != null) {
                binding.exerciseRecycler.adapter = ExerciseBodyPartAdapter(exercisesBodyPart)
            } else {
                // Hata durumu için uygun işlemi yapın
                println("Failed to load exercises")
            }
        })

    }
}
