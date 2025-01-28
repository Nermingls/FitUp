package dev.nermingules.nsapp.ui.exerciseDetail

import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import dev.nermingules.nsapp.databinding.ItemExerciseViewBinding

class ExerciseBodyPartAdapter(private val exercises: List<ExercisesBodyPart>) :
    RecyclerView.Adapter<ExerciseBodyPartAdapter.ExerciseBodyPartViewHolder>() {



    class ExerciseBodyPartViewHolder(val binding: ItemExerciseViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseBodyPartViewHolder {
        val binding = ItemExerciseViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseBodyPartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseBodyPartViewHolder, position: Int) {
        val exercise = exercises[position]
        val context = holder.itemView.context
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        println(exercise.name)

        // Verileri bağlama
        holder.binding.title.text = exercise.name
        holder.binding.description.text = exercise.bodyPart
        val videoUri = Uri.parse(exercise.gifUrl)
        holder.binding.productImage.load(videoUri,imageLoader)
        holder.binding.equipment.text = exercise.equipment
        // Item tıklanma olayı
        holder.itemView.setOnClickListener {
            /*  val intent = Intent(context, ExerciseDetailActivity::class.java).apply {
                  putExtra("EXERCISE_ID", exercise.id)
                   Toast.makeText(requireContext(), "null değil", Toast.LENGTH_LONG).show()
              }
              context.startActivity(intent)*/
        }
    }

    override fun getItemCount() = exercises.size
}
