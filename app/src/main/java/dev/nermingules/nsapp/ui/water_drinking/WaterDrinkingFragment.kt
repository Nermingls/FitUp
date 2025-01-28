package dev.nermingules.nsapp.ui.water_drinking


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.nermingules.nsapp.R
import dev.nermingules.nsapp.database.SharedPreferences
import dev.nermingules.nsapp.databinding.FragmentWaterDrinkingBinding

class WaterDrinkingFragment : Fragment() {

    private var _binding: FragmentWaterDrinkingBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterDrinkingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Context null olabileceğinden yalnızca gerekli durumlarda çağırılmalı
        sharedPreferences = SharedPreferences(requireContext())

        binding.amount.text = sharedPreferences.getWater().toString()
        var waterNum = sharedPreferences.getWater() ?: 0

        binding.positiveFab.setOnClickListener {
            waterNum++
            sharedPreferences.addWater(water = waterNum)
            setGlassImage()
            binding.amount.text = sharedPreferences.getWater().toString()
        }

        binding.negativeFab.setOnClickListener {
            if (waterNum > 0) {
                waterNum--
                sharedPreferences.addWater(water = waterNum)
                setGlassImage()
                binding.amount.text = sharedPreferences.getWater().toString()
            }
        }

        setGlassImage()
    }

    private fun setGlassImage() = when (sharedPreferences.getWater()) {
        0 -> binding.glass.setImageResource(R.drawable.glass)
        1 -> binding.glass.setImageResource(R.drawable.glass2)
        2 -> binding.glass.setImageResource(R.drawable.glass3)
        3 -> binding.glass.setImageResource(R.drawable.glass4)
        else -> binding.glass.setImageResource(R.drawable.glass5)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
