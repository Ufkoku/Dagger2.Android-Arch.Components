package com.ufkoku.archcomponents

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelUtilsTest {

    @Test
    fun getAttachedViewModels() {
        val mockFragment = Mockito.mock(Fragment::class.java)

        val viewModelStore = ViewModelStore()
        val map: MutableMap<String, ViewModel> = viewModelStore.getMapTest()
        val viewModel = object : ViewModel() {}
        map["key"] = viewModel

        Mockito.`when`(mockFragment.viewModelStore).thenReturn(viewModelStore)

        Assert.assertEquals(map.entries, mockFragment.getAttachedViewModels())
    }

    @Test
    fun getViewModels() {
        val viewModelStore = ViewModelStore()
        val map: MutableMap<String, ViewModel> = viewModelStore.getMapTest()
        val viewModel = object : ViewModel() {}
        map["key"] = viewModel

        Assert.assertEquals(map.entries, viewModelStore.getViewModels())
    }

    @Test
    fun contains_True() {
        val viewModelStore = ViewModelStore()
        val map: MutableMap<String, ViewModel> = viewModelStore.getMapTest()
        val viewModel = object : ViewModel() {}
        map["key"] = viewModel

        Assert.assertEquals("key", viewModelStore.contains(viewModel))
    }

    @Test
    fun contains_False() {
        val viewModelStore = ViewModelStore()
        val viewModel = object : ViewModel() {}

        Assert.assertNull(viewModelStore.contains(viewModel))
    }

    @Test
    fun ownes_True() {
        val mockFragment = Mockito.mock(Fragment::class.java)

        val viewModelStore = ViewModelStore()
        val map: MutableMap<String, ViewModel> = viewModelStore.getMapTest()
        val viewModel = object : ViewModel() {}
        map["key"] = viewModel

        Mockito.`when`(mockFragment.viewModelStore).thenReturn(viewModelStore)

        Assert.assertEquals("key", mockFragment.ownes(viewModel))
    }

    @Test
    fun ownes_False() {
        val mockFragment = Mockito.mock(Fragment::class.java)

        val viewModelStore = ViewModelStore()
        val viewModel = object : ViewModel() {}

        Mockito.`when`(mockFragment.viewModelStore).thenReturn(viewModelStore)

        Assert.assertNull(mockFragment.ownes(viewModel))
    }

    @Suppress("UNCHECKED_CAST")
    private fun ViewModelStore.getMapTest(): MutableMap<String, ViewModel> {
        return this::class.java.getDeclaredField("mMap").let {
            it.isAccessible = true
            it.get(this) as MutableMap<String, ViewModel>
        }
    }

}