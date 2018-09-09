package com.ufkoku.archcomponents

import androidx.test.InstrumentationRegistry
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.ufkoku.archcomponents.testable.TestableFragment
import com.ufkoku.archcomponents.testable.TestableFragmentHolderActivity
import com.ufkoku.archcomponents.utils.recreateActivitySync
import com.ufkoku.archcomponents.utils.waitForActivityCreation
import com.ufkoku.archcomponents.utils.waitForFragmentResumed
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@SmallTest
class DaggerArchFragmentTest {

    companion object {
        private const val DATA = "testableData"
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<TestableFragmentHolderActivity>(TestableFragmentHolderActivity::class.java)

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun lifecycleTest() {
        var activity = activityRule.waitForActivityCreation()

        var fragment = TestableFragment.buildFragment(DATA)
        activity.setupFragment(fragment)
        fragment.waitForFragmentResumed()

        Assert.assertFalse(fragment.isSavedInstanceInitialized)
        Assert.assertNotNull(fragment.viewModelSavable)
        Assert.assertEquals(DATA, fragment.viewModelSavable.data)
        Assert.assertNotNull(fragment.viewModelDefault)

        activity = activityRule.recreateActivitySync(instrumentation)

        fragment = activity.getFragment()!!
        fragment.waitForFragmentResumed()

        Assert.assertTrue(fragment.isSavedInstanceInitialized)
        Assert.assertNotNull(fragment.viewModelSavable)
        Assert.assertEquals(DATA, fragment.viewModelSavable.data)
        Assert.assertNotNull(fragment.viewModelDefault)
    }

}