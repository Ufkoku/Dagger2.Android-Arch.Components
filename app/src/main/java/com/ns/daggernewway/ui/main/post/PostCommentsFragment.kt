package com.ns.daggernewway.ui.main.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.daggernewway.R
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.entity.ui.FullPost
import com.ufkoku.archcomponents.DaggerArchFragment
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_comments.*
import javax.inject.Inject
import javax.inject.Named

class PostCommentsFragment : DaggerArchFragment() {

    @Inject
    protected lateinit var viewModel: CommentsViewModel

    private var adapter: CommentAdapter? = null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CommentAdapter(layoutInflater)
        comments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        comments.adapter = adapter

        adapter?.post = viewModel.post

        viewModel.getComments().observe(viewLifecycleOwner, Observer {
            adapter?.postItems(it)
        })
    }

    @Module
    class ArgumentExtractorModule {

        companion object {
            const val QUALIFIER = "PostCommentsFragment.ArgumentExtractorModule"
        }

        @Provides
        @Named(QUALIFIER)
        @FragmentScope
        fun extractFullPost(fragment: PostCommentsFragment): FullPost? {
            return fragment.arguments?.getParcelable(ARG_POST)
        }

        @Provides
        @Named(QUALIFIER)
        @FragmentScope
        fun provideSavedInstanceState(fragment: PostCommentsFragment): Bundle? {
            return fragment.savedInstanceState
        }

    }

}
