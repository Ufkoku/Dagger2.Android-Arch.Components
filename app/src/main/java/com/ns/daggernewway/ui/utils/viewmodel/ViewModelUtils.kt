package com.ns.daggernewway.ui.utils.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.reflect.Field
import java.util.*

fun ViewModelStoreOwner.ownes(viewModel: ViewModel): String? {
    return viewModelStore.contains(viewModel)
}

fun ViewModelStoreOwner.getAttachedViewModels(): Set<Map.Entry<String, ViewModel>> {
    return viewModelStore.getViewModels()
}

private val fViewModelMap: Field by lazy {
    ViewModelStore::class.java
            .declaredFields
            .first { Map::class.java.isAssignableFrom(it.type) }
            .apply { isAccessible = true }
}

fun ViewModelStore.contains(viewModel: ViewModel): String? {
    val pair = getViewModels().firstOrNull { it.value == viewModel }
    return pair?.key
}

@Suppress("UNCHECKED_CAST")
fun ViewModelStore.getViewModels(): Set<Map.Entry<String, ViewModel>> {
    val map = fViewModelMap.get(this) as Map<*, *>?
    return if (map == null) {
        Collections.emptySet()
    } else {
        map.entries as Set<Map.Entry<String, ViewModel>>
    }
}