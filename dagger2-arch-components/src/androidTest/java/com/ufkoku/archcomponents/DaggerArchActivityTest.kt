package com.ufkoku.archcomponents

import androidx.test.InstrumentationRegistry
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.ufkoku.archcomponents.testable.TestableActivity
import com.ufkoku.archcomponents.utils.recreateActivitySync
import com.ufkoku.archcomponents.utils.waitForActivityCreation
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@SmallTest
class DaggerArchActivityTest {

    companion object {
        private const val DATA = "testableData"
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<TestableActivity>(
            TestableActivity::class.java, false, false)

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun lifecycleTest() {
        activityRule.launchActivity(
                TestableActivity.buildIntent(instrumentation.context, DATA))

        var activity = activityRule.waitForActivityCreation()

        Assert.assertFalse(activity.isSavedInstanceInitialized)
        Assert.assertNotNull(activity.viewModelSavable)
        Assert.assertEquals(DATA, activity.viewModelSavable.data)
        Assert.assertNotNull(activity.viewModelDefault)

        activity = activityRule.recreateActivitySync(instrumentation)

        Assert.assertTrue(activity.isSavedInstanceInitialized)
        Assert.assertNotNull(activity.viewModelSavable)
        Assert.assertEquals(DATA, activity.viewModelSavable.data)
        Assert.assertNotNull(activity.viewModelDefault)
    }

}