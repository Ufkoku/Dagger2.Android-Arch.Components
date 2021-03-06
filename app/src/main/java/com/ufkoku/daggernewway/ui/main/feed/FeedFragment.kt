package com.ufkoku.daggernewway.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufkoku.archcomponents.DaggerArchFragment
import com.ufkoku.daggernewway.R
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModel
import com.ufkoku.daggernewway.util.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragment : DaggerArchFragment(), OnItemClickListener<Post> {

    companion object {

        const val TAG = "FeedFragment"

        fun getInstance(): FeedFragment = FeedFragment()

    }

    @Inject
    protected lateinit var viewModel: FeedViewModel

    @Inject
    protected lateinit var router: Router

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_feed, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh.setOnRefreshListener { viewModel.refreshData() }

        val adapter = FeedAdapter(layoutInflater, this)
        feed.adapter = adapter
        feed.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.feed.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        viewModel.feedLoadStatus.observe(viewLifecycleOwner, Observer {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
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

    override fun onItemClicked(item: Post) =
            router.moveToPostComments(item)

    interface Router {

        fun moveToPostComments(post: Post)

    }

}