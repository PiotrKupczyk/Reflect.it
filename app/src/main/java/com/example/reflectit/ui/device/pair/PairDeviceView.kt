package com.example.reflectit.ui.device.pair

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.reflectit.R
import com.goodiebag.pinview.Pinview
import kotlinx.android.synthetic.main.pair_device_fragment.*
import com.example.reflectit.ui.device.available.list.AvailableDevicesViewDirections as AvailableDevicesViewDirections1



class PairDeviceView : Fragment() {

    var mirroIp=""
    var mirrorPort=""

    companion object {
        fun newInstance() = PairDeviceView()
    }

    private lateinit var viewModel: PairDeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pair_device_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            val arg =PairDeviceViewArgs.fromBundle(it)
            mirroIp=arg.mirrorIp
//            mirrorPort=arg.mirrorPort
            mirrorPort="5000"

            //only for testing
            ipTextView.setText(arg.mirrorIp)
            portTextView.setText(mirrorPort)
        }

        viewModel = ViewModelProviders.of(this,  PairDeviceViewModelFactory(PairDeviceRepository(mirroIp, mirrorPort)))
            .get(PairDeviceViewModel::class.java)
        // TODO: Use the ViewModel


        pinView.setPinViewEventListener(Pinview.PinViewEventListener { pinview, fromUser ->
            viewModel.pairDevice(pinview.value).observe(this, Observer {
                if(it==true){
                    val navAction = PairDeviceViewDirections.actionMirrorProfiles()
                        //TODO mirror id here
                    Navigation.findNavController(this.view!!).navigate(navAction)
                }
            })
        })
    }





}
