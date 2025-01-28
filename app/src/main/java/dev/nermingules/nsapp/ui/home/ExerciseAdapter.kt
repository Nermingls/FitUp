package dev.nermingules.nsapp.ui.home

import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import dev.nermingules.nsapp.databinding.CardExercisePreviewBinding

// Veri sınıfını tanımla (JSON yanıtındaki alanlara göre)
data class Exercise(
    val id: String,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val target: String
)

class ExerciseAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    class ExerciseViewHolder(val binding: CardExercisePreviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = CardExercisePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        val context = holder.itemView.context
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        // Verileri bağlama
        holder.binding.title.text = exercise.name
        holder.binding.description.text = exercise.bodyPart
        val videoUri = Uri.parse(exercise.gifUrl)
        holder.binding.productImage.load(videoUri,imageLoader)

        // Item tıklanma olayı
        holder.itemView.setOnClickListener {
       /* val intent = Intent(context, ExerciseDetailActivity::class.java).apply {
                putExtra("EXERCISE_ID", exercise.id)
            }
            context.startActivity(intent)*/
        }
    }

    override fun getItemCount() = exercises.size
}
