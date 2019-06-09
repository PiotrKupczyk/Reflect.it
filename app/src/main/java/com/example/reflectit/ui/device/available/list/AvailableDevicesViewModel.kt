package com.example.reflectit.ui.device.available.list

import android.content.SharedPreferences
import android.net.nsd.NsdManager
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror
import com.example.reflectit.ui.data.services.NetworkService
import com.example.reflectit.ui.extensions.Constant
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Inet4Address
import java.net.InetAddress


class AvailableDevicesViewModel(private val manager: NsdManager) : ViewModel() {

    private val availableDevices = MutableLiveData<ArrayList<Mirror>>()

    fun registerDiscoverService() : LiveData<ArrayList<Mirror>> {
        
        println("Discover registered")
        NetworkService.registerService(manager, port = 5005)
        CoroutineScope(Dispatchers.IO).launch {
            NetworkService.discoverServices(manager) { inetAddress: InetAddress, port: Int ->
                availableDevices.appendAsync(Mirror(inetAddress as Inet4Address, port))
                if (inetAddress is Inet4Address) {
                    availableDevices.appendAsync(Mirror(inetAddress, port))
                }
                //TODO: call api to get mirror specific data such as GUID

            }
        }
        return availableDevices
    }

    fun unregisterDiscoverService() {
        println("Discover unregistered")
        manager.stopServiceDiscovery(NetworkService.listener)
    }
    
}
