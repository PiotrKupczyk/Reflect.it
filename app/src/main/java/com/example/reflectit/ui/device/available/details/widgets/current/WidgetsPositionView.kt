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
import androidx.recyclerview.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import WidgetGridAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetCategory
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModel
import com.example.reflectit.ui.device.available.details.widgets.SharedWidgetsSelectorViewModelFactory
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import com.example.reflectit.ui.extensions.Constant
import kotlinx.android.synthetic.main.device_settings_fragment.*
import kotlinx.android.synthetic.main.toolbar.*


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
        activity?.toolbar?.setTitle(R.string.yourMirror)

        setupViewModel()
        setupGridRecyclerView()

        setupAddButton()

    }

    private fun setupViewModel() {
        val baseUrl = sharedPreferences?.getString(Constant.HOSTNAMEKEY, "") ?: ""
        val token = sharedPreferences?.getString(Constant.TOKEN, "") ?: ""

        viewModel = activity?.run {
            ViewModelProviders.of(
                this,
                SharedWidgetsSelectorViewModelFactory(WidgetsRepository(baseUrl, token))
            ).get(SharedWidgetsSelectorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.selectedWidgets.observe(this, Observer { widgets ->
            widgetsPositionRecyclerView.adapter?.notifyDataSetChanged()
//            viewModel.updateCurrentConfiguration()
            Log.d("G", widgets.toString())
        })
    }


    private fun setupGridRecyclerView() {
        val recyclerView = view?.findViewById(R.id.widgetsPositionRecyclerView) as RecyclerView

        val adapter = WidgetGridAdapter(
            viewModel.selectedWidgets.value ?: mutableListOf(), this.context
        )


        val dragDropManager = RecyclerViewDragDropManager()
        val wrappedAdapter = dragDropManager.createWrappedAdapter(adapter)


        recyclerView.adapter = wrappedAdapter
        recyclerView.layoutManager = object : GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
//        recyclerView.layoutManager = LinearLayoutManager(this.context)

//        (widgetsRecyclerView.itemAnimator as SimpleItemAnimator)
        dragDropManager.setInitiateOnTouch(true)
        dragDropManager.attachRecyclerView(recyclerView)
    }

    private fun setupAddButton() {
        add_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.widgetsSelectorView)
        }
    }

}

