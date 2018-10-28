package com.ufkoku.archcomponents.testable

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.ufkoku.archcomponents.DaggerArchActivity

class TestableFragmentHolderActivity : DaggerArchActivity() {

    private var layout: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = FrameLayout(this)
        layout.id = 1
        setContentView(layout)
        this.layout = layout
    }

    fun setupFragment(fragment: Fragment): Boolean {
        layout?.let {

            supportFragmentManager.beginTransaction()
                    .replace(it.id, fragment)
                    .commit()

            return true
        }
        return false
    }

    fun setupDialog(fragment: DialogFragment): Boolean {
        fragment.show(supportFragmentManager, "DialogTag")
        return true
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Fragment> getFragment(): T? {
        return supportFragmentManager.fragments[0] as T?
    }

}