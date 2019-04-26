package com.example.reflectit.ui.device.available.pair

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import androidx.navigation.Navigation
import com.example.reflectit.R
import com.example.reflectit.ui.extensions.Constant
import kotlinx.android.synthetic.main.pair_device_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.example.reflectit.ui.device.available.list.AvailableDevicesViewDirections as AvailableDevicesViewDirections1


class PairDeviceView : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        initViewModel()
        bindPinView()
    }

    private fun bindPinView() {
        pinView.setPinViewEventListener { pinview, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.pairDevice(pinview.value).observe(this@PairDeviceView, Observer { token ->
                    if (token != null) { //then save token to shared prefs and navigate to device details
                        sharedPreferences.edit {
                            this.putString(Constant.TOKEN, token)
                            apply()
                        }
                        //                    val navAction = PairDeviceViewDirections.actionMirrorProfiles(dummyId)
                        //                    Navigation.findNavController(this.view!!).navigate(navAction)
                    } else { //handle auth error here

                    }
                })
            }
        }
    }

    private fun initViewModel() {
        val hostname = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "")
        viewModel = ViewModelProviders.of(this, PairDeviceViewModelFactory(PairDeviceRepository(hostname!!)))
            .get(PairDeviceViewModel::class.java)
    }


}
