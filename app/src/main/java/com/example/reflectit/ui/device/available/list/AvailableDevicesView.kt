package com.example.reflectit.ui.device.available.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror
import com.example.reflectit.ui.extensions.Constant
import kotlinx.android.synthetic.main.available_devices_fragment.mirrorsList;

class AvailableDevicesView : Fragment() {

    var mirrorList = ArrayList<Mirror>()


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

        viewModelAvailable = ViewModelProviders.of(
            this,
            AvailableDevicesViewModelFactory(AvailableDevicesRepository(context?.getSystemService(Context.NSD_SERVICE) as NsdManager))
        ).get(AvailableDevicesViewModel::class.java)

        bindRecyclerView()
    }

    @SuppressLint("WrongConstant")
    private fun bindRecyclerView() {
        val recyclerView = mirrorsList as RecyclerView
        val mirrorAdapter = MirrorAdapter(mirrorList) { ip, port ->

            val hostnameSharedPref = this.context?.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)!!
            hostnameSharedPref.edit {
                this.putString(Constant.HOSTNAMEKEY, "$ip:$port")
                apply()
            }

            Navigation.findNavController(this.view!!).navigate(R.id.pairDeviceView)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = mirrorAdapter


        viewModelAvailable.getAvailableDevices().observe(this, Observer {
            mirrorAdapter.setData(it)
        })
    }
}



