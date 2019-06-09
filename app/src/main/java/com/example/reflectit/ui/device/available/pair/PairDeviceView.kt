package com.example.reflectit.ui.device.available.pair

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.reflectit.R
import com.example.reflectit.ui.extensions.Constant
import com.goodiebag.pinview.Pinview
import kotlinx.android.synthetic.main.pair_device_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.reflectit.ui.device.available.list.AvailableDevicesViewDirections as AvailableDevicesViewDirections1
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.toolbar.*


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
        activity?.toolbar?.setTitle(R.string.pair)

    }

    override fun onResume() {
        super.onResume()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    override fun onPause() {
        super.onPause()
        view?.hideKeyboard()
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
                        val navAction = PairDeviceViewDirections.actionPairDeviceViewToWidgetsPositioner()
                        Navigation.findNavController(this@PairDeviceView.view!!).navigate(navAction)
                    } else { //handle auth error here
                        clearPin(pinview)
                        errorMessage.visibility=View.VISIBLE
                    }
                })
            }
        }
    }


    private fun clearPin(pinview: Pinview){
        for (i in 0 until pinview.childCount) {
            val child = pinview.getChildAt(i) as EditText
            child.setText("")
        }
        pinview.clearFocus()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun initViewModel() {
        val hostname = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "")
        viewModel = ViewModelProviders.of(this, PairDeviceViewModelFactory(PairDeviceRepository(hostname!!)))
            .get(PairDeviceViewModel::class.java)
    }

}
