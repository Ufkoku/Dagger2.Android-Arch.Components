package com.ufkoku.archcomponents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.reflect.Field

private val fViewModelMap: Field by lazy(LazyThreadSafetyMode.NONE) {
    ViewModelStore::class.java
            .declaredFields
            .first { Map::class.java.isAssignableFrom(it.type) }
            .apply { isAccessible = true }
}

fun ViewModelStoreOwner.ownes(viewModel: ViewModel): String? {
    return viewModelStore.contains(viewModel)
}

fun ViewModelStoreOwner.getAttachedViewModels(): Set<Map.Entry<String, ViewModel>> {
    return viewModelStore.getViewModels()
}

fun ViewModelStore.contains(viewModel: ViewModel): String? {
    val pair = getViewModels().firstOrNull { it.value == viewModel }
    return pair?.key
}

@Suppress("UNCHECKED_CAST")
fun ViewModelStore.getViewModels(): Set<Map.Entry<String, ViewModel>> {
    val map = fViewModelMap.get(this) as Map<String, ViewModel>
    return map.entries
}