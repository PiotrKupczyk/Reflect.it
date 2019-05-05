package com.example.reflectit.ui.device.available.details.widgets.select.parameters

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.reflectit.R
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModel
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModelFactory
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import com.example.reflectit.ui.extensions.Constant


class WidgetParametersProviderView : Fragment() {

    companion object {
        fun newInstance() =
            WidgetParametersProviderView()
    }

    private var sharedPreferences: SharedPreferences? = null

    private lateinit var viewModel: SharedWidgetsSelectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.widget_parameters_provider_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        setupViewModel()
        makeFieldsToFill()
        // TODO: Use the ViewModel
    }

    private fun setupViewModel() {
        val hostname = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "") ?: ""
        val token = sharedPreferences?.getString(Constant.TOKEN, "") ?: ""
        viewModel = activity?.run {
            ViewModelProviders.of(
                this,
                SharedWidgetsSelectorViewModelFactory(WidgetsRepository(hostname, token))
            ).get(SharedWidgetsSelectorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun makeFieldsToFill() {
        val id = arguments?.get("widgetId") as String
        Log.d("Bundle id", id)
//        viewModel.getWidgetBy()
    }



}
