package com.example.reflectit.ui.device.available.details.widgets.select

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.extensions.GlideApp
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class WidgetsSectionAdapter(val selectedWidgets: ArrayList<Widget>, val title: String, val widgets: List<Widget>, val onClickHandler: (widget: Widget) -> Unit): StatelessSection (
    SectionParameters.builder()
        .itemResourceId(R.layout.widgets_list_row)
        .headerResourceId(R.layout.widgets_list_header)
        .build()
) {

//    val selectedWidgets = ArrayList<Widget>()

    override fun getContentItemsTotal(): Int {
        return widgets.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val widgetHolder = holder as WidgetSectionViewHolder

        if(selectedWidgets.contains(widgets[position])){
            widgetHolder.itemView.setBackgroundColor(ContextCompat.getColor(widgetHolder.itemView.context, R.color.colorPrimary))
        }

        GlideApp
            .with(holder.widgetImage.context)
            .load(widgets[position].imageUrl)
            .fitCenter()
            .placeholder(R.drawable.mirror_image)
            .into(holder.widgetImage)

        widgetHolder.widgetName.text = widgets[position].name
        widgetHolder.itemView.setOnClickListener {
            if(selectedWidgets.contains(widgets[position])){
                selectedWidgets.remove(widgets[position])
                it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.white))
            }
            else{
                selectedWidgets.add(widgets[position])
                it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.colorPrimary))
            }
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