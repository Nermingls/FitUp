package dev.nermingules.nsapp.ui.social

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import dev.nermingules.nsapp.databinding.FragmentUploadBinding
import dev.nermingules.nsapp.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class UploadFragment : Fragment() {

    private var selectedPicture: Uri? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLaunchers()

        binding.exploreImage.setOnClickListener {
            checkAndRequestPermissions(it)
        }

        binding.uploadButton.setOnClickListener {
//            savePostToRoom()
            Toast.makeText(requireContext(), "Post saved locally!", Toast.LENGTH_SHORT).show()

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

//    private fun savePostToRoom() {
//        if (selectedPicture == null) {
//            Toast.makeText(requireContext(), "Please select an image first!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val uuid = UUID.randomUUID()
//        val imageName = "$uuid.jpg"
//
//        // Veritabanına kaydedilecek post nesnesi
//        val post = Post(
//            downloadUrl = imageName,  // Fotoğrafı Firebase'den kaldırdık, burada dosya adını kaydediyoruz
//            userEmail = "user@example.com",  // Örnek olarak sabit kullanıcı emaili
//            comment = binding.uploadCommentText.text.toString(),
//            date = Date().toString() // Kendi tarih formatınızla
//        )
//
//        // Room'a kaydetme işlemi
//        val db = Room.databaseBuilder(
//            requireContext(),
//            RDatabase::class.java, "app_database"
//        ).build()
//
//        val postDao = db.postDao()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            postDao.insertPost(post) // Room'a post ekle
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
                        binding.exploreImage.setImageBitmap(bitmap)
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
