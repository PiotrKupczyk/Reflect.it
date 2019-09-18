package com.example.reflectit.ui.data.services

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import java.net.Inet4Address
import java.net.InetAddress

object NetworkService {
    const val SERVICE_TYPE = "_http._tcp."
    //    private const val SERVICE_TYPE = "_services._dns-sd._udp"
    const val SERVICE_NAME = "smartmirror"
     private val logTag = "Network-service"

    var listener: NsdManager.DiscoveryListener? = null

    fun discoverServices(manager: NsdManager, onServiceFoundHandler: (InetAddress, Int) -> Unit) {
        listener = object : NsdManager.DiscoveryListener {
            override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
                if (!serviceInfo?.serviceType.equals(SERVICE_TYPE)) {
                    Log.d(logTag, "Unknown Service FieldType: " + serviceInfo?.serviceType);
                }
//                else if (serviceInfo?.serviceName.equals(SERVICE_NAME)) {
//                    Log.d(logTag, "Same machine: $SERVICE_NAME");
                else if (serviceInfo?.serviceName!!.contains(SERVICE_NAME)) {
                    manager.resolveService(serviceInfo, resolveListener(onServiceFoundHandler))
                }
            }

            override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
                manager.stopServiceDiscovery(this)
                Log.d(logTag, "Failed!!!")
            }

            override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
                manager.stopServiceDiscovery(this)
                Log.d(logTag, "Failed!!!")
            }

            override fun onDiscoveryStarted(serviceType: String?) {
                Log.d(logTag, "Started!!!")

            }

            override fun onDiscoveryStopped(serviceType: String?) {
                Log.d(logTag, "Stopped!!!")
            }

            override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
                Log.d(logTag, "Lost!!!")
            }
        }

        manager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, listener)
    }

    fun registerService(manager: NsdManager, port: Int) {
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = SERVICE_NAME
            serviceType = SERVICE_TYPE
            setPort(port)
        }
        manager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, object : NsdManager.RegistrationListener {

            override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
//                mServiceName = NsdServiceInfo.serviceName
            }

            override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                // Registration failed! Put debugging code here to determine why.
            }

            override fun onServiceUnregistered(arg0: NsdServiceInfo) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
            }

            override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                // Unregistration failed. Put debugging code here to determine why.
            }
        })

    }

    private fun resolveListener(onServiceFoundHandler: (host: InetAddress, port: Int) -> Unit): NsdManager.ResolveListener {
        return object : NsdManager.ResolveListener {
            override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                Log.d(logTag, "Resolve failed$errorCode");
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
                Log.d(logTag, "Resolve Succeeded. $serviceInfo")
                if (serviceInfo?.host is Inet4Address)
                    onServiceFoundHandler(serviceInfo.host, serviceInfo.port)

                    if (serviceInfo?.serviceName == SERVICE_NAME) {
                    Log.d(logTag, "Same IP.")
                    return
                }
            }
        }
    }

}