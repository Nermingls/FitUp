package  dev.nermingules.nsapp.adapter.medicineAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.nermingules.nsapp.databinding.MedicineRecyclerRowBinding
import dev.nermingules.nsapp.model.Medicine

class MedicineAdapter(
    private val medicines: List<Medicine>,
) :
    RecyclerView.Adapter<MedicineHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        val from = LayoutInflater.from(parent.context)
        val binding =
            MedicineRecyclerRowBinding.inflate(from, parent, false)
        return MedicineHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        holder.bindMedicine(medicines[position])
    }

    override fun getItemCount(): Int = medicines.size

}