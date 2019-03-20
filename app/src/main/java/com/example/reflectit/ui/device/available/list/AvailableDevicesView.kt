package com.example.reflectit.ui.device.available.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reflectit.R

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
        viewModelAvailable = ViewModelProviders.of(this).get(AvailableDevicesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
