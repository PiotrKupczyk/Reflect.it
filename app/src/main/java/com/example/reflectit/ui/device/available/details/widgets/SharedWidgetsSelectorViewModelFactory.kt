package com.example.reflectit.ui.device.available.details.widgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reflectit.ui.device.available.pair.PairDeviceRepository
import com.example.reflectit.ui.device.available.pair.PairDeviceViewModel

class SharedWidgetsSelectorViewModelFactory(private val repository: WidgetsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedWidgetsSelectorViewModel(repository) as T
    }
}