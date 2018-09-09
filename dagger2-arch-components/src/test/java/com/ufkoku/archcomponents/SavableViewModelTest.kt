package com.ufkoku.archcomponents

import android.os.Bundle
import com.ufkoku.archcomponents.testable.TestableViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SavableViewModelTest {

    companion object {
        const val SUFFIX_1 = "VM_1"
        const val DATA_1 = "Data1"

        const val SUFFIX_2 = "VM_2"
        const val DATA_2 = "Data2"
    }

    @Test
    fun save_SingleNoSuffix() {
        val viewModel1 = TestableViewModel("", DATA_1)

        val overallBundle = Bundle()
        viewModel1.save(overallBundle)

        Assert.assertTrue(overallBundle.containsKey(buildBundleKey("")))
    }

    @Test
    fun save_Multiple() {
        val viewModel1 = TestableViewModel(SUFFIX_1, DATA_1)
        val viewModel2 = TestableViewModel(SUFFIX_2, DATA_2)

        val overallBundle = Bundle()
        viewModel1.save(overallBundle)
        viewModel2.save(overallBundle)

        Assert.assertTrue(overallBundle.containsKey(buildBundleKey(SUFFIX_1)))
        Assert.assertTrue(overallBundle.containsKey(buildBundleKey(SUFFIX_2)))

        Assert.assertNotEquals(
                overallBundle.getBundle(buildBundleKey(SUFFIX_1)),
                overallBundle.getBundle(buildBundleKey(SUFFIX_2)))

        Assert.assertEquals(DATA_1, overallBundle.getBundle(buildBundleKey(SUFFIX_1))!!.getString(TestableViewModel.KEY_DATA))
        Assert.assertEquals(DATA_2, overallBundle.getBundle(buildBundleKey(SUFFIX_2))!!.getString(TestableViewModel.KEY_DATA))
    }

    @Test
    fun restore_SingleNoSuffix() {
        var viewModel1 = TestableViewModel("", DATA_1)

        val overallBundle = Bundle()
        viewModel1.save(overallBundle)

        viewModel1 = TestableViewModel("", overallBundle)

        Assert.assertEquals(DATA_1, viewModel1.data)
    }

    @Test
    fun restore_Multiple() {
        var viewModel1 = TestableViewModel(SUFFIX_1, DATA_1)
        var viewModel2 = TestableViewModel(SUFFIX_2, DATA_2)

        val overallBundle = Bundle()
        viewModel1.save(overallBundle)
        viewModel2.save(overallBundle)

        viewModel1 = TestableViewModel(SUFFIX_1, overallBundle)
        viewModel2 = TestableViewModel(SUFFIX_2, overallBundle)

        Assert.assertEquals(DATA_1, viewModel1.data)
        Assert.assertEquals(DATA_2, viewModel2.data)
    }

    private fun buildBundleKey(suffix: String): String {
        val basicKey = TestableViewModel::class.java.simpleName
        return if (suffix.isEmpty()) {
            basicKey
        } else {
            "$basicKey$$suffix"
        }
    }


}