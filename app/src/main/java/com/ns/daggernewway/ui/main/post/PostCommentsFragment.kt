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
import com.ns.daggernewway.domain.ui.entity.Post
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ns.daggernewway.ui.main.post.viewmodel.ICommentsViewModel
import com.ns.daggernewway.util.fLazy
import com.ufkoku.archcomponents.DaggerArchFragment
import kotlinx.android.synthetic.main.fragment_comments.*
import javax.inject.Inject

class PostCommentsFragment : DaggerArchFragment() {

    companion object {

        const val TAG = "PostCommentsFragment"

        private const val ARG_POST = "PostCommentsFragment.Post"

        fun getInstance(post: Post): PostCommentsFragment {
            val instance = PostCommentsFragment()

            val args = Bundle()
            args.putParcelable(ARG_POST, post)
            instance.arguments = args

            return instance
        }

    }

    val post: Post by fLazy { arguments?.getParcelable<Post>(ARG_POST)!! }

    @Inject
    protected lateinit var viewModel: ICommentsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_comments, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh.setOnRefreshListener { viewModel.refreshData() }

        val adapter = CommentAdapter(layoutInflater)
        comments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        comments.adapter = adapter

        adapter.submitItems(post, null)

        viewModel.comments.observe(viewLifecycleOwner, Observer { adapter.submitItems(post, it) })

        viewModel.commentsLoadStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                GeneralFlowStatus.IDLE -> swipeRefresh.isRefreshing = false
                GeneralFlowStatus.IN_PROGRESS -> swipeRefresh.isRefreshing = true
                GeneralFlowStatus.COMPLETED -> viewModel.moveLoadStatusToIdle()
                GeneralFlowStatus.FAILED -> {
                    Toast.makeText(context!!, R.string.app_error_general, Toast.LENGTH_LONG).show()
                    viewModel.moveLoadStatusToIdle()
                }
            }
        })
    }

}
