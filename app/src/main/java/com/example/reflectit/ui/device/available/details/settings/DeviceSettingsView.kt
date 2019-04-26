package com.example.reflectit.ui.device.available.details.settings

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidgets
import com.woxthebox.draglistview.DragListView
import kotlinx.android.synthetic.main.device_settings_fragment.*


class DeviceSettingsView : Fragment() {

    var widget1 = RemoteWidgets(1, "a", "a", R.drawable.mirror_image)
    var widget2 = RemoteWidgets(2, "a", "a", R.drawable.mirror_image)
    var widget3 = RemoteWidgets(3, "a", "a", R.drawable.mirror_image)
    var widget4 = RemoteWidgets(4, "a", "a", R.drawable.mirror_image)
    var widget5 = RemoteWidgets(5, "a", "a", R.drawable.mirror_image)
    var widget6 = RemoteWidgets(6, "a", "a", R.drawable.mirror_image)
    var widget7 = RemoteWidgets(7, "a", "a", R.drawable.mirror_image)
    var widget8 = RemoteWidgets(8, "a", "a", R.drawable.mirror_image)
    var widget9 = RemoteWidgets(9, "a", "a", R.drawable.mirror_image)


    var widgets = ArrayList<RemoteWidgets>()
    lateinit var mDraglistView: DragListView


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

        mDraglistView = drag_list_view


        widgets.add(widget1)
        widgets.add(widget2)
        widgets.add(widget3)
        widgets.add(widget4)
        widgets.add(widget5)
        widgets.add(widget6)
        widgets.add(widget7)
        widgets.add(widget8)
        widgets.add(widget9)

        setupGridRecyclerView()
        setupAddButton()
    }


    private fun setupGridRecyclerView() {
        mDraglistView.setLayoutManager(GridLayoutManager(context, 3))
        val listAdapter = ItemAdapter(widgets, R.layout.grid_item, R.id.item_layout, true)
        mDraglistView.setAdapter(listAdapter, true)
        mDraglistView.setCanDragHorizontally(true)
        mDraglistView.setCustomDragItem(null)
        mDraglistView.setScrollingEnabled(false)
    }

    private fun setupAddButton() {
        add_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.widgetsView)
        }
    }

}


