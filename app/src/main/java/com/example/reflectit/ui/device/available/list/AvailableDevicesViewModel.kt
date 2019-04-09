package com.example.reflectit.ui.device.available.list

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror
import com.example.reflectit.ui.extensions.Constant


class AvailableDevicesViewModel(private val repository: AvailableDevicesRepository) : ViewModel() {

//    init {
//        repository.getAvailableDevices().observeForever {
//            //TODO: handle found mirrors
//            mirrorList=it
//        }
//    }

    fun getAvailableDevices() : LiveData<ArrayList<Mirror>> {
        return repository.getAvailableDevices()
    }

    fun saveBaseUrl(sharedPreferences: SharedPreferences, ip: String, port: String) {
        sharedPreferences.edit {
            this.putString(Constant.HOSTNAMEKEY, "http:/$ip:$port")
            commit()
        }
        //here save to ip and port to shared prefs
    }
}
