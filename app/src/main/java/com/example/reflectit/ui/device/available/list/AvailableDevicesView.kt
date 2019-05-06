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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.available_devices_fragment.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar

@SuppressLint("ValidFragment")
class AvailableDevicesView : Fragment() {

    var mirrorList = ArrayList<Mirror>()


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
            AvailableDevicesViewModelFactory(AvailableDevicesRepository(context?.getSystemService(Context.NSD_SERVICE) as NsdManager))
        ).get(AvailableDevicesViewModel::class.java)

        activity?.toolbar?.setTitle(R.string.availableMirrors)


        bindRecyclerView()
//        val btn_click_me = view?.findViewById(R.id.button_scan) as Button;
//        btn_click_me.setOnClickListener {
//            val manager = context?.getSystemService(Context.NSD_SERVICE)
//            val availableDevices = MutableLiveData<ArrayList<Mirror>>()
//            NetworkService.discoverServices(manager as NsdManager) { inetAddress: InetAddress, port: Int ->
//                //TODO: call api to get mirror specific data such as GUID
//                availableDevices.appendAsync(Mirror(inetAddress, port))
//                println("Mirror posted")
//            }
//            Log.d("pairdevice", "aaaaaaaaaaassssssssaaaa")
//        };
    }

    @SuppressLint("WrongConstant")
    private fun bindRecyclerView() {
        val recyclerView = mirrorsList as RecyclerView
        val mirrorAdapter = MirrorAdapter(mirrorList) { ip, port ->

            val hostnameSharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            hostnameSharedPref.edit {
                this.putString(Constant.HOSTNAMEKEY, "$ip:$port")
                apply()
            }

            Navigation.findNavController(this.view!!).navigate(R.id.pairDeviceView)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = mirrorAdapter


        viewModel.getAvailableDevices().observe(this, Observer {
            mirrorAdapter.setData(it)
        })
    }
}



