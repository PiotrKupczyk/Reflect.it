package com.example.reflectit.ui.device.available.list

import android.annotation.SuppressLint
import android.content.Context
import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
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
import kotlinx.android.synthetic.main.available_devices_fragment.*
import kotlinx.android.synthetic.main.toolbar.toolbar

@SuppressLint("ValidFragment")
class AvailableDevicesView : Fragment() {

    var mirrorList = ArrayList<Mirror>()
    private lateinit var mirrorAdapter: MirrorAdapter


    companion object {
        fun newInstance() = AvailableDevicesView()
    }

    private lateinit var viewModel: AvailableDevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.available_devices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            AvailableDevicesViewModelFactory(context?.getSystemService(Context.NSD_SERVICE) as NsdManager)
        ).get(AvailableDevicesViewModel::class.java)

        activity?.toolbar?.setTitle(R.string.availableMirrors)


        bindRecyclerView()
    }


    override fun onPause() {
        super.onPause()
        viewModel.unregisterDiscoverService()
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerDiscoverService().observe(this, Observer {
            mirrorAdapter.setData(it)
        })
    }

    @SuppressLint("WrongConstant")
    private fun bindRecyclerView() {
        val recyclerView = mirrorsList as RecyclerView
        mirrorAdapter = MirrorAdapter(mirrorList) { ip, port ->

            val hostnameSharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            hostnameSharedPref.edit {
                this.putString(Constant.HOSTNAMEKEY, "$ip:$port")
                apply()
            }

            Navigation.findNavController(this.view!!).navigate(R.id.pairDeviceView)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = mirrorAdapter


        viewModel.registerDiscoverService().observe(this, Observer {
            mirrorAdapter.setData(it)
        })
    }
}



