package com.example.reflectit.ui.device.available.list

import android.annotation.SuppressLint
import android.content.Context
import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror
import kotlinx.android.synthetic.main.available_devices_fragment.mirrorsList;

class AvailableDevicesView : Fragment() {

    var mirrorList = ArrayList<Mirror>()
    var mirrorAdapter = MirrorAdapter(mirrorList)

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

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = mirrorsList as RecyclerView
        recyclerView.layoutManager=LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        recyclerView.adapter=mirrorAdapter

        viewModelAvailable = ViewModelProviders.of(
            this,
            AvailableDevicesViewModelFactory(
                context?.getSystemService(Context.NSD_SERVICE) as NsdManager,
                AvailableDevicesRepository(context?.getSystemService(Context.NSD_SERVICE) as NsdManager)
            )
        ).get(AvailableDevicesViewModel::class.java)

        viewModelAvailable.getAvailableDevices().observe(this, Observer {
            mirrorAdapter.setData(it)
        })
    }
}



