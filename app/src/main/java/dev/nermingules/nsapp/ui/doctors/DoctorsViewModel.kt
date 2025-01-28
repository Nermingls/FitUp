//package dev.nermingules.nsapp.ui.doctors
//
//import android.app.Application
//import androidx.lifecycle.MutableLiveData
//import dev.nermingules.nsapp.base.BaseViewModel
//import dev.nermingules.nsapp.database.RDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//
//class DoctorsViewModel(application: Application) : BaseViewModel(application) {
//
//    val doctors = MutableLiveData<List<Doctor>>()
//    fun getDataFromSQLite() {
//        launch(Dispatchers.IO) {
//            try {
//                val doctorList = RDatabase(getApplication()).doctorsDao().getAllDoctors()
//                // Burada postValue kullanıyoruz, çünkü IO iş parçacığında çalışıyoruz
//                showDoctors(doctorList)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Hata yönetimi
//            }
//        }
//    }
//
//    private fun showDoctors(doctorList: List<Doctor>) {
//        // postValue kullanarak UI thread'de güncelleme yapıyoruz
//        doctors.postValue(doctorList)
//    }
//
//}
