package com.ns.daggernewway.ui.main.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.ui.FullPost
import com.ufkoku.archcomponents.DaggerArchFragment
import kotlinx.android.synthetic.main.fragment_comments.*
import javax.inject.Inject

class PostCommentsFragment : DaggerArchFragment() {

    companion object {

        private const val ARG_POST = "PostCommentsFragment.FullPost"

        fun getInstance(post: FullPost): PostCommentsFragment {
            val instance = PostCommentsFragment()

            val args = Bundle()
            args.putParcelable(ARG_POST, post)
            instance.arguments = args

            return instance
        }

    }

    val fullPost: FullPost? by lazy {
        arguments?.getParcelable<FullPost>(ARG_POST)
    }

    @Inject
    protected lateinit var viewModel: CommentsViewModel

    private var adapter: CommentAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CommentAdapter(layoutInflater)
        comments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        comments.adapter = adapter

        adapter?.post = viewModel.post

        viewModel.getComments().observe(viewLifecycleOwner, Observer {
            adapter?.postItems(it)
        })
    }

}
