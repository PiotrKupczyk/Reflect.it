package com.example.reflectit.ui.device.pair

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PairDeviceViewModelFactory (private val repository: PairDeviceRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PairDeviceViewModel(repository) as T
    }


}