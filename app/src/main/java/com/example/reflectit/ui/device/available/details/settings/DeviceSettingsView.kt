package com.example.reflectit.ui.device.available.details.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidgets
import kotlinx.android.synthetic.main.device_settings_fragment.*

class DeviceSettingsView : Fragment() {

    var widget1=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget2=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget3=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget4=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget5=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget6=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)
    var widget7=RemoteWidgets(1,"a", "a", R.drawable.mirror_image)

    var widgets = ArrayList<RemoteWidgets>()



    companion object {
        fun newInstance() = DeviceSettingsView()
    }

    private lateinit var viewModel: DeviceSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.device_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeviceSettingsViewModel::class.java)
        // TODO: Use the ViewModel

        widgets.add(widget1)
        widgets.add(widget2)
        widgets.add(widget3)
        widgets.add(widget4)
        widgets.add(widget5)
        widgets.add(widget6)
        widgets.add(widget7)
        bindAdapter()
    }

    private fun bindAdapter() {
        var adapter = GridViewAdapter(this.context!!, widgets , 3)
        val gridView= dynamic_grid
        gridView.adapter=adapter


    }

}
