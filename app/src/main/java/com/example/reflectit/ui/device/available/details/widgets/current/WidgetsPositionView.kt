package com.example.reflectit.ui.device.available.details.widgets.current

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModel
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModelFactory
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import com.example.reflectit.ui.extensions.Constant
import kotlinx.android.synthetic.main.device_settings_fragment.*
import kotlinx.android.synthetic.main.widgets_fragment.*


class WidgetsPositionView : Fragment() {

    private var sharedPreferences: SharedPreferences? = null

    companion object {
        fun newInstance() = WidgetsPositionView()
    }

    private lateinit var viewModel: SharedWidgetsSelectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.device_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        setupViewModel()
        setupGridRecyclerView()
        setupAddButton()

    }

    private fun setupViewModel() {
        val baseUrl = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "") ?: ""
        val token = sharedPreferences?.getString(Constant.TOKEN, "") ?: ""

        viewModel = activity?.run {
            ViewModelProviders.of(this,
                SharedWidgetsSelectorViewModelFactory(WidgetsRepository(baseUrl, token))
            ).get(SharedWidgetsSelectorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.selectedWidgets.observe(this, Observer { widgets ->
            drag_list_view.adapter.notifyDataSetChanged()
            viewModel.updateCurrentConfiguration()
            Log.d("G", widgets.toString())
            //handle selected widget
        })
    }


    private fun setupGridRecyclerView() {
        val dragListView = drag_list_view
        dragListView.setLayoutManager(GridLayoutManager(context, 3))
        val listAdapter = ItemAdapter(
            viewModel.selectedWidgets.value ?: arrayListOf(),
            R.layout.grid_item,
            R.id.item_layout,
            true
        )
        dragListView.setAdapter(listAdapter, true)
        dragListView.setCanDragHorizontally(true)
        dragListView.setCustomDragItem(null)
        dragListView.setScrollingEnabled(false)
    }

    private fun setupAddButton() {
        add_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.widgetsSelectorView)
        }
    }

}


