package com.example.reflectit.ui.device.available.list

import android.net.nsd.NsdManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.models.Mirror


class AvailableDevicesViewModel(manager: NsdManager, private val repository: AvailableDevicesRepository) : ViewModel() {

    var mirrors = MutableLiveData <ArrayList<Mirror>>()

//    init {
//        repository.getAvailableDevices().observeForever {
//            //TODO: handle found mirrors
//            mirrorList=it
//        }
//    }

    fun getAvailableDevices() : LiveData<ArrayList<Mirror>> {
        return repository.getAvailableDevices()
    }
}
