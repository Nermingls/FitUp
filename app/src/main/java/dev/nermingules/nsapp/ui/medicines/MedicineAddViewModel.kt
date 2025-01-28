package dev.nermingules.nsapp.ui.medicines

import android.app.Application
import dev.nermingules.nsapp.base.BaseViewModel
import dev.nermingules.nsapp.database.RDatabase
import dev.nermingules.nsapp.model.Medicine

import kotlinx.coroutines.launch

class MedicineAddViewModel(application: Application) : BaseViewModel(application) {

    fun storeInSQLite(medicine: Medicine) {
        launch {
            val dao = RDatabase(getApplication()).medicinesDao()
            dao.insertMedicine(medicine)
        }
    }

}