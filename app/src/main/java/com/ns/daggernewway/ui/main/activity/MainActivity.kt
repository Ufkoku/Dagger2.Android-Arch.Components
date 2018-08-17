package com.ns.daggernewway.ui.main.activity

import android.os.Bundle
import com.ns.daggernewway.R
import com.ufkoku.archcomponents.DaggerArchActivity
import com.ns.daggernewway.ui.main.feed.FeedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerArchActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(mainRoot.id, FeedFragment())
                    .commit()
        }

    }

}
