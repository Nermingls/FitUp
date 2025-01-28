package dev.nermingules.nsapp.ui.doctors

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.nermingules.nsapp.adapter.doctorAdapter.DoctorAdapter
import dev.nermingules.nsapp.adapter.medicineAdapter.MedicineAdapter
import dev.nermingules.nsapp.databinding.FragmentDoctorsBinding
import dev.nermingules.nsapp.repository.doctorList


class DoctorsFragment : Fragment() {

    private var _binding: FragmentDoctorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
//    private lateinit var viewModel: DoctorsViewModel

//    val doctorArrayList : ArrayList<Doctor> = ArrayList()
//    var adapter : DoctorAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDoctorsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addDoctor.setOnClickListener {
            findNavController().navigate(DoctorsFragmentDirections.actionDoctorsFragmentToAddDoctorFragment())
        }
//        viewModel = ViewModelProvider(requireActivity()).get(DoctorsViewModel::class.java)
//        viewModel.getDataFromSQLite()

        binding.doctorsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DoctorAdapter(doctorList) { url ->
                goToWebsite(url)
            }
        }


//        observeLiveData()

    }
//
//    private fun observeLiveData() {
//        viewModel.doctors.observe(viewLifecycleOwner) { doctorlist ->
//            doctorlist?.let {
//                updateRecyclerList(doctorlist)
//            }
//        }
//    }

//    private fun updateRecyclerList(doctorlist: List<Doctor>) {
//        binding.doctorsRecycler.adapter =
//            MedicineAdapter(doctorlist)
//
//    }
private fun goToWebsite(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}
}