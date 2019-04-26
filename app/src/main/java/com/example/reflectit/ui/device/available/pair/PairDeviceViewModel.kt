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
//        CoroutineScope(Dispatchers.Default).launch
         return runBlocking {
             return@runBlocking repository.pair(code)
        }
    }

}
