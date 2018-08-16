package com.ns.daggernewway.ui.main.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.ui.FullPost

class CommentAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        const val TYPE_POST = 1

        const val TYPE_COMMENT = 2

    }

    var post: FullPost? = null
        set(value) {
            if (field != value) {
                when {
                    field == null -> {
                        field = value
                        notifyItemInserted(0)
                    }
                    value == null -> {
                        field = value
                        notifyItemRemoved(0)
                    }
                    else -> {
                        field = value
                        notifyItemChanged(0)
                    }
                }
            }
        }

    private var comments: List<Comment> = ArrayList()

    fun postItems(new: List<Comment>) {
        val result = DiffUtil.calculateDiff(DiffCallback(comments, new))
        comments = ArrayList(new)

        val offset = if (post == null) 0 else 1

        result.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                notifyItemChanged(position + offset)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition + offset, toPosition + offset)
            }

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeChanged(position + offset, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position + offset, count)
            }
        })
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
            TYPE_POST -> (holder as PostViewHolder).bind(post!!)
            TYPE_COMMENT -> (holder as CommentViewHolder).bind(comments[if (post == null) position else position - 1])
            else -> throw IllegalArgumentException("Unsupported view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        var itemsSize = comments.size

        if (post != null) {
            itemsSize++
        }

        return itemsSize
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && post != null) {
            return TYPE_POST
        }

        return TYPE_COMMENT
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val author: TextView by lazy { view.findViewById<TextView>(R.id.author) }

        val text: TextView by lazy { view.findViewById<TextView>(R.id.text) }

        fun bind(post: FullPost) {
            author.text = post.user.name
            text.text = post.body
        }

    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val author: TextView by lazy { view.findViewById<TextView>(R.id.author) }

        val text: TextView by lazy { view.findViewById<TextView>(R.id.text) }

        fun bind(comment: Comment) {
            author.text = comment.name
            text.text = comment.body
        }

    }

    private class DiffCallback(private val oldList: List<Comment>,
                               private val newList: List<Comment>) : DiffUtil.Callback() {

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