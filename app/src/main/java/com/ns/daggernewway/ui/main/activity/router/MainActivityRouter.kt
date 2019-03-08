package com.ns.daggernewway.ui.main.activity.router

import androidx.fragment.app.transaction
import com.ns.daggernewway.R
import com.ns.daggernewway.domain.ui.entity.Post
import com.ns.daggernewway.ui.main.activity.MainActivity
import com.ns.daggernewway.ui.main.feed.FeedFragment
import com.ns.daggernewway.ui.main.post.PostCommentsFragment

class MainActivityRouter(private val activity: MainActivity) : IMainActivityRouter {

    init {
        activity.supportFragmentManager.addOnBackStackChangedListener {
            activity.supportActionBar?.let {
                val show = activity.supportFragmentManager.backStackEntryCount > 0
                it.setDisplayHomeAsUpEnabled(show)
            }
        }
    }

    override fun moveToStart() = activity.supportFragmentManager.transaction {
        replace(R.id.mainRoot, FeedFragment.getInstance(), FeedFragment.TAG)
    }

    override fun moveToPostComments(post: Post) = activity.supportFragmentManager.transaction {
        replace(R.id.mainRoot, PostCommentsFragment.getInstance(post), PostCommentsFragment.TAG)
        addToBackStack(PostCommentsFragment.TAG)
    }

}