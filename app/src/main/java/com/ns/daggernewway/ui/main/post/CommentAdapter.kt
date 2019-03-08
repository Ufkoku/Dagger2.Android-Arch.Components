package com.ns.daggernewway.ui.main.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.domain.ui.entity.Comment
import com.ns.daggernewway.domain.ui.entity.Post

class CommentAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        const val TYPE_POST = 1

        const val TYPE_COMMENT = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                    when {
                        oldItem is Post && newItem is Post -> oldItem.id == newItem.id
                        oldItem is Comment && newItem is Comment -> oldItem.id == newItem.id
                        else -> false
                    }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                    oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer<Any>(this, DIFF_CALLBACK)

    fun submitItems(post: Post?, comments: List<Comment>?) {
        val size = (if (post != null) 1 else 0) + (comments?.size ?: 0)
        val newList = ArrayList<Any>(size)
        if (post != null) newList.add(post)
        if (comments != null) newList.addAll(comments)
        differ.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_POST -> PostViewHolder(inflater.inflate(R.layout.list_item_comments_post, parent, false))
            TYPE_COMMENT -> CommentViewHolder(inflater.inflate(R.layout.list_item_comments_comment, parent, false))
            else -> throw IllegalArgumentException("Unsupported view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_POST -> (holder as PostViewHolder).bind(differ.currentList[position] as Post)
            TYPE_COMMENT -> (holder as CommentViewHolder).bind(differ.currentList[position] as Comment)
            else -> throw IllegalArgumentException("Unsupported view type $viewType")
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int =
            if (position == 0 && differ.currentList[0] is Post) TYPE_POST
            else TYPE_COMMENT

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val author: TextView = view.findViewById(R.id.author)

        private val text: TextView = view.findViewById(R.id.text)

        fun bind(post: Post) {
            author.text = post.user.email
            text.text = post.body
        }

    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val author: TextView = view.findViewById(R.id.author)

        private val text: TextView = view.findViewById(R.id.text)

        fun bind(comment: Comment) {
            author.text = comment.email
            text.text = comment.body
        }

    }

}