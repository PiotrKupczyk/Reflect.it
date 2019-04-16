package com.example.reflectit.ui.device.available.pair

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;

class PairDeviceViewModel(private val repository: PairDeviceRepository) : ViewModel() {

    fun pairDevice(code: String) : LiveData<String?>{
        return repository.pair(code)
    }
}
