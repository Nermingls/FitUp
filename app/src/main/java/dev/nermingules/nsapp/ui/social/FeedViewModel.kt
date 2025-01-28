package dev.nermingules.nsapp.ui.social

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dev.nermingules.nsapp.base.BaseViewModel
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.model.Medicine
import dev.nermingules.nsapp.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    val post = MutableLiveData<List<Post>>()
//
//    fun getDataFromSQLite() {
//        launch(Dispatchers.IO) {
//            try {
//                val postList = RDatabase(getApplication()).postDao().getAllPosts()
//                // Burada postValue kullanıyoruz, çünkü IO iş parçacığında çalışıyoruz
//                showPost(postList)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Hata yönetimi
//            }
//        }
//    }

    private fun showPost(postList: List<Post>) {
        // postValue kullanarak UI thread'de güncelleme yapıyoruz
        post.postValue(postList)
    }

}
