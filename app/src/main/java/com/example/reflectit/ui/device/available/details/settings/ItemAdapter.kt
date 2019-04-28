package com.example.reflectit.ui.device.available.details.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.RemoteWidgets
import com.woxthebox.draglistview.DragItemAdapter
import java.util.ArrayList

internal class ItemAdapter(
    list: ArrayList<RemoteWidgets>,
    private val mLayoutId: Int,
    private val mGrabHandleId: Int,
    private val mDragOnLongPress: Boolean
) : DragItemAdapter<RemoteWidgets, ItemAdapter.ViewHolder>() {

    init {
        itemList = list
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mLayoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val id = mItemList[position].image
        holder.gridImage.setImageResource(id)
        holder.itemView.tag = mItemList[position]
    }

    override fun getUniqueItemId(position: Int): Long {
        return mItemList[position].id.toLong()
    }

    internal inner class ViewHolder(itemView: View):
        DragItemAdapter.ViewHolder(itemView, mGrabHandleId, mDragOnLongPress) {

        var gridImage= itemView.findViewById(R.id.gridImage) as (ImageView)

        override fun onItemClicked(view: View?) {
            Toast.makeText(view!!.context, adapterPosition.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}