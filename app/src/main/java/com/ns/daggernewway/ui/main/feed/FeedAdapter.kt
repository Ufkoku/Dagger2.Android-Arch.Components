package com.ns.daggernewway.ui.main.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.ui.utils.recyclerview.OnItemClickListener
import java.util.*

class FeedAdapter(private val inflater: LayoutInflater,
                  private val onItemClickListener: OnItemClickListener<FullPost>) : RecyclerView.Adapter<FeedAdapter.PostViewHolder>() {

    private var items: List<FullPost> = Collections.emptyList()

    fun postItems(new: List<FullPost>) {
        val result = DiffUtil.calculateDiff(DiffCallback(items, new))
        items = ArrayList(new)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
                inflater.inflate(R.layout.list_item_feed_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val author: TextView by lazy { view.findViewById<TextView>(R.id.author) }

        private val text: TextView by lazy { view.findViewById<TextView>(R.id.text) }

        private lateinit var binded: FullPost

        init {
            view.setOnClickListener {
                if (::binded.isInitialized) {
                    onItemClickListener.onItemClicked(binded)
                }
            }
        }

        fun bind(post: FullPost) {
            binded = post

            author.text = post.user.name
            text.text = post.body
        }

    }

    private class DiffCallback(private val oldList: List<FullPost>,
                               private val newList: List<FullPost>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

    }

}