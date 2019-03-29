package com.example.reflectit.ui.device.available.list

import android.content.Context
import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.reflectit.R
import kotlinx.android.synthetic.main.available_devices_fragment.*


class AvailableDevicesView : Fragment() {

    companion object {
        fun newInstance() = AvailableDevicesView()
    }

    private lateinit var viewModelAvailable: AvailableDevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.available_devices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelAvailable = ViewModelProviders.of(this,
            AvailableDevicesViewModelFactory(context?.getSystemService(Context.NSD_SERVICE) as NsdManager,
                                            AvailableDevicesRepository(context?.getSystemService(Context.NSD_SERVICE) as NsdManager)))
            .get(AvailableDevicesViewModel::class.java)
    }
}


