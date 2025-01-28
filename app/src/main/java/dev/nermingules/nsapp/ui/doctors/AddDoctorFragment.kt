package dev.nermingules.nsapp.ui.doctors

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.databinding.FragmentAddDoctorBinding
import dev.nermingules.nsapp.model.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class AddDoctorFragment : Fragment() {

    private var selectedPicture: Uri? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var _binding: FragmentAddDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLaunchers()

        binding.doctorWomenImage.setOnClickListener {
            checkAndRequestPermissions(it)
        }

        binding.uploadButton.setOnClickListener {
            Toast.makeText(requireContext(), "Post saved locally!", Toast.LENGTH_SHORT).show()
//            savePostToRoom()
        }
    }

    private fun checkAndRequestPermissions(view: View) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissions.all { perm -> ContextCompat.checkSelfPermission(requireContext(), perm) == PackageManager.PERMISSION_GRANTED }) {
            openGallery()
        } else {
            if (shouldShowRequestPermissionRationale(permissions.first())) {
                Snackbar.make(view, "Permission needed to access the gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Grant Permission") {
                        permissionLauncher.launch(permissions)
                    }.show()
            } else {
                permissionLauncher.launch(permissions)
            }
        }
    }

    private fun openGallery() {
        val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intentToGallery)
    }

//
//    private fun uploadImageToFirebase() {
//        if (selectedPicture == null) {
//            Toast.makeText(requireContext(), "Please select an image first!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val uuid = UUID.randomUUID()
//        val imageName = "$uuid.jpg"
//        val storage = Firebase.storage
//        val reference = storage.reference.child("images/$imageName")
//
//        reference.putFile(selectedPicture!!).addOnSuccessListener {
//            reference.downloadUrl.addOnSuccessListener { uri ->
//                val downloadUrl = uri.toString()
//
//                val doctorsMap = hashMapOf(
//                "name" to binding.hospitalName.text.toString(),
//                "hospital" to binding.hospitalName.text.toString(),
//                "medicalSpecialty" to binding.medicalSpecialty.text.toString(),
//                "comment" to binding.comment.text.toString(),
//                "downloadUrl" to downloadUrl,
//                "uuid" to uuid,
//                "date" to  Timestamp.now())
//
//
//                Firebase.firestore.collection("Doctors")
//                    .add(doctorsMap)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(requireContext(), "AddDoctors Successful!", Toast.LENGTH_SHORT).show()
//                            requireActivity().onBackPressedDispatcher.onBackPressed()
//                        } else {
//                            Toast.makeText(requireContext(), "Failed to save post!", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            }
//        }.addOnFailureListener { e ->
//            Toast.makeText(requireContext(), "AddDoctors failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
//            Log.d("AddDoctors","Upload failed: ${e.localizedMessage}")
//
//        }
//    }
//
//    private fun savePostToRoom() {
//        val uuid = UUID.randomUUID()
//
//        // Veritabanına kaydedilecek post nesnesi
//        val doctor = Doctor(
//            name = binding.name.text.toString(),
//            gender = binding.gender.text.toString(),
//            hospital =binding.hospitalName.text.toString() ,
//            medicalSpecialty = binding.medicalSpecialty.text.toString(),
//            comment = binding.comment.text.toString(),
//        )
//
//        // Room'a kaydetme işlemi
//        val db = Room.databaseBuilder(
//            requireContext(),
//            RDatabase::class.java, "app_database"
//        ).build()
//
//        val doctorDao = db.doctorsDao()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            doctorDao.insertDoctor(doctor)// Room'a post ekle
//            withContext(Dispatchers.Main) {
//                Toast.makeText(requireContext(), "Post saved locally!", Toast.LENGTH_SHORT).show()
//                requireActivity().onBackPressedDispatcher.onBackPressed() // Geriye dön
//            }
//        }
//    }
    private fun registerLaunchers() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Permission needed!", Toast.LENGTH_SHORT).show()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intentFromResult = result.data
                selectedPicture = intentFromResult?.data
                try {
                    selectedPicture?.let { uri ->
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uri))
                        } else {
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                        }
                        binding.doctorWomenImage.setImageBitmap(bitmap)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed to load image!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
