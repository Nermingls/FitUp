package dev.nermingules.nsapp.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.nermingules.nsapp.R
import dev.nermingules.nsapp.adapter.mealCardAdapter.MealAdapter
import dev.nermingules.nsapp.adapter.medicineAdapter.MedicineAdapter
import dev.nermingules.nsapp.databinding.FragmentMealsBinding
import dev.nermingules.nsapp.model.Meal


class MealsFragment : Fragment() {

    private lateinit var viewModel: MealsViewModel

    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MealsViewModel::class.java)
        viewModel.getDataFromSQLite()

        binding.mealsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MedicineAdapter(emptyList())
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mealsFragment_to_addMealFragment)
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.meals.observe(viewLifecycleOwner) { mealList ->
            mealList?.let {
                updateRecyclerList(mealList)
            }
        }
    }

    private fun updateRecyclerList(mealList: List<Meal>) {
        binding.mealsRecyclerView.adapter =
            MealAdapter(mealList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}