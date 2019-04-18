package com.example.reflectit.ui.device.available.details.settings

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.ui.data.models.RemoteWidgets
import com.example.reflectit.ui.device.available.list.MirrorAdapter

class WidgetAdapter(var widgets: ArrayList<RemoteWidgets>) : RecyclerView.Adapter<WidgetAdapter.WidgetHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetAdapter.WidgetHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = widgets.size


    override fun onBindViewHolder(holder: WidgetAdapter.WidgetHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class WidgetHolder(v: View) : RecyclerView.ViewHolder(v) {

    }


}
