package dev.nermingules.nsapp.adapter.doctorAdapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.nermingules.nsapp.databinding.ItemDoctorCardBinding
import dev.nermingules.nsapp.model.Doctor
import dev.nermingules.nsapp.ui.exerciseDetail.targetBodyPart
import dev.nermingules.nsapp.ui.home.HomePageFragmentDirections



class DoctorAdapter(private val doctorList: List<Doctor>,
                    private val onDoctorClick: (String) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.PostHolder>() {

    class PostHolder(val binding: ItemDoctorCardBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemDoctorCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return doctorList.size

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.doctorName.text = doctorList.get(position).name
        holder.binding.hospitalName.text = doctorList.get(position).hospital
        holder.binding.medicalSpecialty.text = doctorList.get(position).medicalSpecialty
        holder.binding.comment.text = doctorList.get(position).comment
        Picasso.get().load(doctorList[position].imgUrl).into(holder.binding.doctorImg)
        holder.itemView.setOnClickListener {
            onDoctorClick("https://mhrs.gov.tr")
        }
    }


}
