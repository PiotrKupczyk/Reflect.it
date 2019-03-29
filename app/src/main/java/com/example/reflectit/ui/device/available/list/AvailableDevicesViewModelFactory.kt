package com.example.reflectit.ui.device.available.list

import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AvailableDevicesViewModelFactory(private val manager: NsdManager, private val repository: AvailableDevicesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AvailableDevicesViewModel(manager, repository) as T
    }
}