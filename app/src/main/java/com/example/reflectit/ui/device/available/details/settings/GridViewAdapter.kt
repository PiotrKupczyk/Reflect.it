package com.example.reflectit.ui.device.available.details.settings

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.askerov.dynamicgrid.BaseDynamicGridAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidgets
import kotlinx.android.synthetic.main.grid_item.view.*


class GridViewAdapter(context: Context, var widgets: ArrayList<RemoteWidgets>, columnCount: Int) : BaseDynamicGridAdapter(context, widgets, columnCount) {

    @SuppressLint("ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val remoteWidgets=widgets[position]

        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null)
            holder = ViewHolder(convertView!!)
            convertView!!.setTag(holder)
        } else {
            holder = convertView.getTag() as ViewHolder
        }

        val id = remoteWidgets.image
        holder.gridImage.setImageResource(id)
        return convertView
    }

    class ViewHolder(v: View) {
        var gridImage= v.findViewById(R.id.gridImage) as (ImageView)
    }
}




