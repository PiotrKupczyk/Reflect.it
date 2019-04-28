package com.example.reflectit.ui.device.available.details.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidgets
import com.example.reflectit.ui.device.available.list.MirrorAdapter

class WidgetAdapter(var widgets: ArrayList<RemoteWidgets>) : RecyclerView.Adapter<WidgetAdapter.WidgetHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return WidgetHolder(inflatedView)
    }

    override fun getItemCount() = widgets.size


    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        //TODO przypisac image do imageView
    }

    class WidgetHolder(v: View) : RecyclerView.ViewHolder(v) {
        var widgetImage = v.findViewById(R.id.gridImage) as ImageView
    }


}
