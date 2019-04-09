package com.example.reflectit.ui.device.available.pair

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.reflectit.R
import kotlinx.android.synthetic.main.pair_device_fragment.*
import com.example.reflectit.ui.device.available.list.AvailableDevicesViewDirections as AvailableDevicesViewDirections1



class PairDeviceView : Fragment() {
    lateinit var mirroIp: String
    lateinit var mirrorPort: String

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
            ipTextView.text = arg.mirrorIp
            portTextView.text = mirrorPort
        }

        viewModel = ViewModelProviders.of(this,  PairDeviceViewModelFactory(PairDeviceRepository(mirroIp, mirrorPort)))
            .get(PairDeviceViewModel::class.java)
        // TODO: Use the ViewModel


        pinView.setPinViewEventListener { pinview, _ ->
            viewModel.pairDevice(pinview.value).observe(this, Observer {
                if(it){
                    val dummyId = "Dummy id" //TODO mirror id here
//                    val navAction = PairDeviceViewDirections.actionMirrorProfiles(dummyId)
//                    Navigation.findNavController(this.view!!).navigate(navAction)
                }
            })
        }
    }





}
