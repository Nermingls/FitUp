package dev.nermingules.nsapp.ui.medicines

import android.app.Application
import androidx.lifecycle.MutableLiveData

import dev.nermingules.nsapp.base.BaseViewModel
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.model.Medicine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicinesViewModel(application: Application) : BaseViewModel(application) {

    val medicines = MutableLiveData<List<Medicine>>()
    fun getDataFromSQLite() {
       launch(Dispatchers.IO) {
            try {
                val medicineList = RDatabase(getApplication()).medicinesDao().getAllMedicine()
                // Burada postValue kullanıyoruz, çünkü IO iş parçacığında çalışıyoruz
                showMedicines(medicineList)
            } catch (e: Exception) {
                e.printStackTrace()
                // Hata yönetimi
            }
        }
    }

    private fun showMedicines(medicineList: List<Medicine>) {
        // postValue kullanarak UI thread'de güncelleme yapıyoruz
        medicines.postValue(medicineList)
    }

}
