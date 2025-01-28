package dev.nermingules.nsapp.ui.emergencyCall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.nermingules.nsapp.R
import dev.nermingules.nsapp.databinding.FragmentEmergencyCallBinding

class EmergencyCallFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmergencyCallBinding.inflate(inflater, container, false)

        // Simgeye tıklama işlemi
        binding.ambulance.setOnClickListener {
            val emergencyNumber = "112" // Acil servis numarası
            makeEmergencyCall(emergencyNumber)
        }

        binding.police.setOnClickListener {
            val emergencyNumber = "155" // Acil servis numarası
            makeEmergencyCall(emergencyNumber)
        }
        binding.fireStation.setOnClickListener {
            val emergencyNumber = "110" // Acil servis numarası
            makeEmergencyCall(emergencyNumber)
        }

        return binding.root
    }

    private fun makeEmergencyCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        // Kullanıcıyı arama yapma ekranına yönlendirin
        startActivity(callIntent)

        // Eğer doğrudan arama yapacaksanız CALL_PHONE iznini kontrol edin.
        /*
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            callIntent.action = Intent.ACTION_CALL
            startActivity(callIntent)
        } else {
            // İzin yoksa izin isteme
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION_REQUEST_CODE
            )
        }
        */
    }

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 100
    }
}
