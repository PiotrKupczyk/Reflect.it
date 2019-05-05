package com.example.reflectit.ui.device.available.pair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PairDeviceViewModel(private val repository: PairDeviceRepository) : ViewModel() {

    fun pairDevice(code: String): LiveData<String?> {
        val result = MutableLiveData<String?>()
//        CoroutineScope(Dispatchers.Default).launch { result.value = repository.pair(code) }
        runBlocking { result.value = repository.pair(code) }
        return result
    }

}
