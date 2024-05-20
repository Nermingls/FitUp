package dev.nermingules.nsapp.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.nermingules.nsapp.base.BaseFragment
import dev.nermingules.nsapp.databinding.FragmentHomePageBinding
import dev.nermingules.nsapp.exerciseDetail.ExerciseListViewModel

class HomePageFragment  :  BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    private val viewModel: ExerciseViewModel by viewModels()
    private val targetViewModel: TargetViewModel by viewModels()
    private val exerciseListViewModel: ExerciseListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView'un layout manager'ını belirleyin (yatay olarak)
        binding.productRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        // Verileri gözlemleyin ve RecyclerView'a adapte edin
        viewModel.getExercises().observe(viewLifecycleOwner, Observer { exercises ->
            if (exercises != null) {
                binding.productRecycler.adapter = ExerciseAdapter(exercises)
            } else {
                // Hata durumu için uygun işlemi yapın
                handleLoadingError()
            }
        })

        targetViewModel.getTargetList().observe(viewLifecycleOwner, Observer { targets ->
            // RecyclerView'a adapter'ı ata
            binding.categoryRecycler.adapter = TargetAdapter(targets ?: emptyList())
        })

    }

    private fun handleLoadingError() {
        // Hata durumunda kullanıcıya bilgi vermek için uygun bir yöntem seçin.
        // Örneğin, bir Toast mesajı gösterebilirsiniz.
        Toast.makeText(requireContext(), "Failed to load exercises", Toast.LENGTH_SHORT).show()
    }
}
