package dev.nermingules.nsapp.adapter.feedAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.nermingules.nsapp.databinding.ItemCategoryCardBinding
import dev.nermingules.nsapp.model.Feed
import dev.nermingules.nsapp.model.Post

class FeedAdapter(private val feedList : List<Feed>) : RecyclerView.Adapter<FeedAdapter.PostHolder>() {

    class PostHolder(val binding: ItemCategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemCategoryCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return feedList.size

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.time.text = feedList.get(position).postedAgo
        holder.binding.commentDescription.text = feedList.get(position).description
        holder.binding.userName.text=feedList.get(position).userNickName
        holder.binding.konum.text=feedList.get(position).localName
        Picasso.get().load(feedList[position].imageUrl).into(holder.binding.exploreImage)
        Picasso.get().load(feedList[position].userAvatar).into(holder.binding.userImage)

    }

}