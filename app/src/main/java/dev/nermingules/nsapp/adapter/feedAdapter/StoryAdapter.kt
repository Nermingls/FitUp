package dev.nermingules.nsapp.adapter.feedAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.nermingules.nsapp.databinding.ItemCategoryCardBinding
import dev.nermingules.nsapp.databinding.ItemStoryCardBinding
import dev.nermingules.nsapp.model.Post
import dev.nermingules.nsapp.model.Story

class StoryAdapter(private val postList : List<Story>) : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    class StoryHolder(val binding: ItemStoryCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val binding = ItemStoryCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size

    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.binding.commentDescription.text = postList.get(position).userNickName
        Picasso.get().load(postList[position].userAvatar).into(holder.binding.exploreImage)
    }

}