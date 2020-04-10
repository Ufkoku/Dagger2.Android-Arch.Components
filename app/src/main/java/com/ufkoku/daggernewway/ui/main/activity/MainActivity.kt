package com.ufkoku.daggernewway.ui.main.activity

import android.os.Bundle
import android.view.MenuItem
import com.ufkoku.archcomponents.DaggerArchActivity
import com.ufkoku.daggernewway.R
import com.ufkoku.daggernewway.ui.main.activity.router.MainActivityRouter
import javax.inject.Inject


class MainActivity : DaggerArchActivity() {

    @Inject
    protected lateinit var router: MainActivityRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            router.moveToStart()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}
