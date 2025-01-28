package dev.nermingules.nsapp.ui.medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.nermingules.nsapp.util.createDailyMedicineWorkRequest
import dev.nermingules.nsapp.databinding.FragmentMedicineAddBinding
import dev.nermingules.nsapp.model.Medicine


class MedicineAddFragment : Fragment() {

    private lateinit var viewModel: MedicineAddViewModel

    private var _binding: FragmentMedicineAddBinding? = null
    private val binding get() = _binding!!

    private var chosenHour = 0
    private var chosenMin = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val descriptionText = binding.editTextMedicine
        val button = binding.addMedicineButton
        val timePicker = binding.timePickerMedicine

        viewModel = ViewModelProvider(requireActivity()).get(MedicineAddViewModel::class.java)


        timePicker.setOnTimeChangedListener { _, hour, minute ->
            chosenHour = hour
            chosenMin = minute
        }

        button.setOnClickListener {

            createDailyMedicineWorkRequest(
                chosenHour, chosenMin,
                descriptionText.text.toString(), requireContext()
            )


            viewModel.storeInSQLite(
                Medicine(
                descriptionText.text.toString(),
                "$chosenHour:$chosenMin"
            )
            )

            Toast.makeText(requireContext(), "İlaç Hatırlatıcısı Oluşturuldu!", Toast.LENGTH_SHORT)
                .show()

            val action =
                MedicineAddFragmentDirections.actionMedicineAddFragment2ToMedicinesFragment()
            findNavController().navigate(action)
        }
    }

}