package  dev.nermingules.nsapp.adapter.medicineAdapter

import androidx.recyclerview.widget.RecyclerView
import dev.nermingules.nsapp.databinding.MedicineRecyclerRowBinding
import dev.nermingules.nsapp.model.Medicine

class MedicineHolder(
    private val medicinesBinding: MedicineRecyclerRowBinding,

    ) : RecyclerView.ViewHolder(medicinesBinding.root) {

    fun bindMedicine(medicine: Medicine) {
        medicinesBinding.medicineText.text = medicine.name
        medicinesBinding.timeText.text = medicine.time

    }
}