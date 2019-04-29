package com.example.reflectit.ui.device.available.details.widgets.select

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidget
import com.example.reflectit.ui.data.services.Widget
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class WidgetsSection(val title: String, val widgets: List<Widget>, val onClickHandler: (widget: Widget) -> Unit): StatelessSection (
    SectionParameters.builder()
        .itemResourceId(R.layout.widget_select)
        .headerResourceId(R.layout.header_select)
        .build()
) {
    override fun getContentItemsTotal(): Int {
        return widgets.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val widgetHolder = holder as WidgetSectionViewHolder
        widgetHolder.widgetName.text = widgets[position].name
        widgetHolder.itemView.setOnClickListener {
            onClickHandler(widgets[position])
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return WidgetSectionViewHolder(view!!)
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return WidgetSectionHeaderHolder(view!!)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val headerHolder = holder as WidgetSectionHeaderHolder
        headerHolder.sectionTitle.text = title
    }

}

class WidgetSectionViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val widgetImage = view.findViewById<ImageView>(R.id.widgetImage)
    val widgetName = view.findViewById<TextView>(R.id.widgetName)
}

class WidgetSectionHeaderHolder(view: View): RecyclerView.ViewHolder(view) {
    val sectionTitle = view.findViewById<TextView>(R.id.title)
}