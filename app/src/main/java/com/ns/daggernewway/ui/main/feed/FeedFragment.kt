package com.ns.daggernewway.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.ui.base.fragment.AppFragment
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import com.ns.daggernewway.ui.utils.recyclerview.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragment : AppFragment(), OnItemClickListener<FullPost> {

    @Inject
    protected lateinit var viewModel: FeedViewModel

    private var adapter: FeedAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FeedAdapter(layoutInflater, this)
        feed.adapter = adapter
        feed.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.getFeed().observe(viewLifecycleOwner, Observer {
            adapter!!.postItems(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun onItemClicked(item: FullPost) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.mainRoot, PostCommentsFragment.getInstance(item))
                ?.addToBackStack(null)
                ?.commit()
    }

}