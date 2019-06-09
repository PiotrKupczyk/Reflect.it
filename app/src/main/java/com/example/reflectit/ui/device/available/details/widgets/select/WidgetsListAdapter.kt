package com.example.reflectit.ui.device.available.details.widgets.select

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.extensions.GlideApp
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlin.collections.ArrayList

class WidgetsSectionAdapter(
    private val sectionAdapter: SectionedRecyclerViewAdapter,
    private val selectedWidgets: ArrayList<Widget>,
    val title: String,
    val widgets: List<Widget>,
    val onClickHandler: (widget: Widget) -> Unit
) : StatelessSection(
    SectionParameters.builder()
        .itemResourceId(R.layout.widgets_list_row)
        .headerResourceId(R.layout.widgets_list_header)
        .build()
) {

    private var expanded = false


    override fun getContentItemsTotal(): Int {
        return if (expanded) widgets.size else 0
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val widgetHolder = holder as WidgetSectionViewHolder

        if (selectedWidgets.contains(widgets[position])) {
            widgetHolder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    widgetHolder.itemView.context,
                    R.color.colorPrimary
                )
            )
        }

        GlideApp
            .with(holder.widgetImage.context)
            .load(widgets[position].imageUrl)
            .fitCenter()
            .placeholder(R.drawable.mirror_image)
            .into(holder.widgetImage)

        widgetHolder.widgetName.text = widgets[position].name

        widgetHolder.itemView.setOnClickListener {
            if (selectedWidgets.contains(widgets[position])) {
                selectedWidgets.remove(widgets[position])
                it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.white))
            } else {
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
        headerHolder.expandImageSwitcher.addAnimateExpand(R.anim.slide_in_right, R.anim.slide_out_left)
        headerHolder.expandImageSwitcher.setOnClickListener {
            expanded = !expanded
            headerHolder.handleExpandedChanged()
            sectionAdapter.notifyAllItemsChangedInSection(widgets[0].category.name)
//            sectionAdapter.notifyDataSetChanged()
        }
    }

    inner class WidgetSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val widgetImage = view.findViewById<ImageView>(R.id.widgetImage)
        val widgetName = view.findViewById<TextView>(R.id.widgetName)
    }

    inner class WidgetSectionHeaderHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val widgetCategoryImage = view.findViewById<ImageView>(R.id.widgetCategoryImage)
        val sectionTitle = view.findViewById<TextView>(R.id.title)
        val expandImageSwitcher = view.findViewById<ImageSwitcher>(R.id.expandImageSwitcher)

        fun handleExpandedChanged() {
            if (this@WidgetsSectionAdapter.expanded) {
                expandImageSwitcher.setImageResource(R.drawable.expand_less)
                sectionTitle.setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
                widgetCategoryImage.setColorFilter(ContextCompat.getColor(view.context, R.color.colorPrimary))
                expandImageSwitcher.currentView.apply {
                    val imageView = this as ImageView
                    imageView.setColorFilter(ContextCompat.getColor(view.context, R.color.colorPrimary))
                }
            } else {
                expandImageSwitcher.setImageResource(R.drawable.expand_more)
                sectionTitle.setTextColor(ContextCompat.getColor(view.context, R.color.accent_material_dark))
                widgetCategoryImage.setColorFilter(ContextCompat.getColor(view.context, R.color.accent_material_dark))
                expandImageSwitcher.currentView.apply {
                    val imageView = this as ImageView
                    imageView.setColorFilter(ContextCompat.getColor(view.context, R.color.accent_material_dark))
                }
            }
        }
    }

    private fun ImageSwitcher.addAnimateExpand(inAnimationId: Int, outAnimationId: Int) {
        removeAllViews()
        this.setFactory {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
            imageView
        }
        setImageResource(if (expanded) R.drawable.expand_less else R.drawable.expand_more)
        val `in` = AnimationUtils.loadAnimation(context, inAnimationId)
        inAnimation = `in`

        val out = AnimationUtils.loadAnimation(context, outAnimationId)
        outAnimation = out
    }
}



