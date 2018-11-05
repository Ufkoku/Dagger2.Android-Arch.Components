package com.ns.daggernewway.ui.main.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.ui.common.viewmodel.postcomments.CommentsViewModel
import com.ns.daggernewway.ui.common.viewmodel.status.IStatus
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

    val fullPost: FullPost? by lazy(LazyThreadSafetyMode.NONE) {
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
            if (it == null) {
                return@Observer
            }

            @Suppress("NON_EXHAUSTIVE_WHEN")
            when(it.state){
                IStatus.State.COMPLETED -> adapter?.postItems(it.data!!)
                IStatus.State.ERROR -> Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

}
