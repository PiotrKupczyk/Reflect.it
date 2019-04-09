package com.example.reflectit.ui.device.available.pair

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;

class PairDeviceViewModel(private val repository: PairDeviceRepository) : ViewModel() {
//    fun pairDevice(code: String) {
//        repository.pair(code)
//    }

    fun pairDevice(code: String) : LiveData<Boolean>{
        return repository.pair(code)
    }
}
