package dev.nermingules.nsapp.ui.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.nermingules.nsapp.adapter.feedAdapter.FeedAdapter
import dev.nermingules.nsapp.adapter.feedAdapter.StoryAdapter
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.databinding.FragmentFeedBinding
import dev.nermingules.nsapp.model.Post
import dev.nermingules.nsapp.repository.feedList
import dev.nermingules.nsapp.repository.stories
import dev.nermingules.nsapp.ui.medicines.MedicinesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeedViewModel

    // Bu kısmı Firebase'den kaldırdık çünkü sadece Room kullanılacak
    val postArrayList : ArrayList<Post> = ArrayList()
    var adapter : FeedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        // Feed Recycler için ayarlar
        binding.feedRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FeedAdapter(feedList)
        binding.feedRecycler.adapter = adapter

        // Story Recycler için ayarlar
        binding.storyRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val storyAdapter = StoryAdapter(stories)
        binding.storyRecycler.adapter = storyAdapter
        // Room veritabanından veriyi çekme işlemi
//        getDataFromRoom()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        viewModel = ViewModelProvider(requireActivity()).get(FeedViewModel::class.java)

//        viewModel.getDataFromSQLite()
//        observeLiveData()

    }

    private fun initViews() {

        setOnClickListener()
    }

//
//    private fun observeLiveData() {
//        viewModel.post.observe(viewLifecycleOwner) { postList ->
//            postList?.let {
//                updateRecyclerList(postList)
//            }
//        }
//    }

//    private fun updateRecyclerList(postList: List<Post>) {
//        binding.feedRecycler.adapter =
//            FeedAdapter(postList)
//
//    }
    private fun setOnClickListener() {
        binding.apply {
            addPost.setOnClickListener {
                findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToUploadFragment2())
            }
        }
    }

    // Room veritabanından veri çekmek için fonksiyon
//    private fun getDataFromRoom() {
//        val db = RDatabase.invoke(requireContext()) // Room veritabanı instance'ı oluşturuluyor
//        val postDao = db.postDao() // PostDAO'yu al
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val postsFromDb = postDao.getAllPosts() // Room'dan verileri çekiyoruz
//                withContext(Dispatchers.Main) {
//                    postArrayList.clear() // Önceki verileri temizliyoruz
//                    postArrayList.addAll(postsFromDb.map { postEntity ->
//                        // Veritabanındaki PostEntity modelini Post modeline dönüştürüyoruz
//                        Post(
//                            userEmail = postEntity.userEmail,
//                            comment = postEntity.comment,
//                            downloadUrl = postEntity.downloadUrl,
//                            date = postEntity.date
//                        )
//                    })
//                    adapter?.notifyDataSetChanged() // RecyclerView'daki veriyi güncelliyoruz
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
//                    Log.e("FeedFragment","Error: ${e.localizedMessage}")
//                }
//            }
//        }
//    }
}
