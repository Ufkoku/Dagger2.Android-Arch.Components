package com.ufkoku.daggernewway.ui.main.activity.router

import androidx.fragment.app.commit
import com.ufkoku.daggernewway.R
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.main.activity.MainActivity
import com.ufkoku.daggernewway.ui.main.feed.FeedFragment
import com.ufkoku.daggernewway.ui.main.post.PostCommentsFragment

interface MainActivityRouter: FeedFragment.Router {

    fun moveToStart()

}

class MainActivityRouterImpl(private val activity: MainActivity) : MainActivityRouter {

    init {
        activity.supportFragmentManager.addOnBackStackChangedListener {
            activity.supportActionBar?.let {
                val show = activity.supportFragmentManager.backStackEntryCount > 0
                it.setDisplayHomeAsUpEnabled(show)
            }
        }
    }

    override fun moveToStart() = activity.supportFragmentManager.commit {
        replace(R.id.mainRoot, FeedFragment.getInstance(), FeedFragment.TAG)
    }

    override fun moveToPostComments(post: Post) = activity.supportFragmentManager.commit {
        replace(R.id.mainRoot, PostCommentsFragment.getInstance(post), PostCommentsFragment.TAG)
        addToBackStack(PostCommentsFragment.TAG)
    }

}