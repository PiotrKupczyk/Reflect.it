package com.example.reflectit.ui.device.available.list

import android.net.nsd.NsdManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;


class AvailableDevicesViewModel(manager: NsdManager, private val repository: AvailableDevicesRepository) : ViewModel() {

    init {
        repository.getAvailableDevices().observeForever {
            //TODO: handle found mirrors
        }
    }
}
