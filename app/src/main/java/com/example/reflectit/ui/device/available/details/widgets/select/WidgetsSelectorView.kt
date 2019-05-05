package com.example.reflectit.ui.device.available.details.widgets.select

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetCategory
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModel
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModelFactory
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import com.example.reflectit.ui.extensions.Constant
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.widgets_fragment.*

class WidgetsSelectorView : Fragment() {

    private var sharedPreferences: SharedPreferences? = null

    companion object {
        fun newInstance() = WidgetsSelectorView()
    }

    private lateinit var viewModel: SharedWidgetsSelectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.widgets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        // TODO: Use the ViewModel
        bindRecyclerView()
        setupOkButton()

    }

    private fun initViewModel() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val hostname = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "") ?: ""
        val token = sharedPreferences?.getString(Constant.TOKEN, "") ?: ""

        viewModel = activity?.run {
            ViewModelProviders.of(
                this,
                SharedWidgetsSelectorViewModelFactory(WidgetsRepository(hostname, token))
            ).get(SharedWidgetsSelectorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun setupOkButton() {
        ok_button.setOnClickListener {
            val currentValue = viewModel.selectedWidgets.value
            viewModel.selectedWidgets.postValue(ArrayList(currentValue!!.fillWithPlaceholders(9-currentValue.size)))
            Navigation.findNavController(it).navigateUp()
        }
    }

    private fun bindRecyclerView() {
        val sectionAdapter = SectionedRecyclerViewAdapter()
        viewModel.getAllWidgets().observe(this, Observer { widgets ->
            WidgetCategory.values().forEach { widgetCategory ->
                sectionAdapter.addSection(
                    widgetCategory.name,
                    WidgetsSection(widgetCategory.name.toUpperCase(),
                        widgets.filter { it.category == widgetCategory }
                    ) {
                        viewModel.selectWidget(it)
                        navigateToWidgetParametersProvider(it.id)
                    }
                )
            }
            widgetsRecyclerView?.adapter = sectionAdapter
            (widgetsRecyclerView.adapter as SectionedRecyclerViewAdapter).notifyDataSetChanged()
        })

        val recyclerView = widgetsRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = sectionAdapter

    }

    private fun navigateToWidgetParametersProvider(widgetId: Int) {
        val bundle = bundleOf("widgetId" to widgetId)
//        val navArgument = NavArgument.Builder()
//            .setType(NavType.IntType)
//            .setDefaultValue(widgetId)
//            .build()
//        val navDestination = findNavController()
//            .graph
//            .findNode(R.id.widgetParametersProviderView)
//            ?.addArgument("widgetId", navArgument)
//        Navigation.findNavController(this.view!!).navigate(R.id.widgetParametersProviderView, bundle)
//        Navigation.findNavController(this.view!!).navigateUp()
    }
}

fun MutableCollection<Widget>.fillWithPlaceholders(howMany: Int): MutableCollection<Widget> {
    val result = this.filter { it.category != WidgetCategory.Placeholder }.toMutableList()
    for (i in 0..howMany)
        result.add(result.lastIndex+1, Widget(i+10, "empty", WidgetCategory.Placeholder, ""))
    return result
}

