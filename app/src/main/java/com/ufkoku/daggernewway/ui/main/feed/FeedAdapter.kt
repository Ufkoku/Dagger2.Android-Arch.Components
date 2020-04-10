package com.ufkoku.daggernewway.ui.main.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ufkoku.daggernewway.R
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.util.OnItemClickListener

class FeedAdapter(private val inflater: LayoutInflater,
                  private val onItemClickListener: OnItemClickListener<Post>)
    : ListAdapter<Post, FeedAdapter.PostViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
                inflater.inflate(R.layout.list_item_feed_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val author: TextView = view.findViewById(R.id.author)

        private val text: TextView = view.findViewById(R.id.text)

        private lateinit var binded: Post

        init {
            view.setOnClickListener {
                onItemClickListener.onItemClicked(binded)
            }
        }

        fun bind(post: Post) {
            binded = post

            author.text = post.user.email
            text.text = post.body
        }

    }

}