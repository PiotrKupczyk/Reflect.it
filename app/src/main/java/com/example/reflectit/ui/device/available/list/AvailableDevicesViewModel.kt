package com.example.reflectit.ui.device.available.list

import android.net.nsd.NsdManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reflectit.ui.data.models.Mirror
import com.example.reflectit.ui.data.services.NetworkService
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Inet4Address
import java.net.InetAddress


class AvailableDevicesViewModel(private val manager: NsdManager) : ViewModel() {

    private val availableDevices = MutableLiveData<ArrayList<Mirror>>()
    private val TAG = "AvailableDevicesVM"

    fun findDevices() : LiveData<ArrayList<Mirror>> {
        Log.d(TAG,"Finding available devices..")
        CoroutineScope(Dispatchers.IO).launch {
            NetworkService.discoverServices(manager) { inetAddress: InetAddress, port: Int ->
                availableDevices.appendAsync(Mirror(inetAddress as Inet4Address, port))
                if (inetAddress is Inet4Address) {
                    Log.d(TAG,"Found available device. Address: ${inetAddress}, port: ${port}")
                    availableDevices.appendAsync(Mirror(inetAddress, port))
                }
            }
        }
        return availableDevices
    }

    fun unregisterDiscoverService() {
        Log.d(TAG,"Discover unregistered")
        manager.stopServiceDiscovery(NetworkService.listener)
    }
    
}
