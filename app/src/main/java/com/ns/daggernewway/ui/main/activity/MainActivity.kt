package com.ns.daggernewway.ui.main.activity

import android.os.Bundle
import android.view.MenuItem
import com.ns.daggernewway.R
import com.ns.daggernewway.ui.main.activity.router.IMainActivityRouter
import com.ufkoku.archcomponents.DaggerArchActivity
import javax.inject.Inject


class MainActivity : DaggerArchActivity() {

    @Inject
    protected lateinit var router: IMainActivityRouter

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
