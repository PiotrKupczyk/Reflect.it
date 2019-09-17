package com.example.reflectit.ui.device.available.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror
import com.example.reflectit.ui.extensions.GlideApp

class MirrorAdapter(private var mirrors: ArrayList<Mirror>,
                    val cellOnClickHandler: (ip: String, port: String) -> Unit) : RecyclerView.Adapter<MirrorAdapter.MirrorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MirrorHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.mirror_item, parent, false)
        return MirrorHolder(inflatedView)
    }

    override fun getItemCount() = mirrors.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MirrorHolder, position: Int) {
        val mirror: Mirror = mirrors[position]
        holder.mirrorName.setText(R.string.defaultMirrorName)
        GlideApp
            .with(holder.mirrorImage.context)
            .load("https://files.gitter.im/evancohen/smart-mirror/letL/icon.png")
            .fitCenter()
            .placeholder(R.drawable.mirror_image)
            .into(holder.mirrorImage)
//        holder.mirrorImage.setImageResource(R.drawable.mirror_image)
        holder.itemView.setOnClickListener {
            cellOnClickHandler(mirror.ip.toString(), mirror.port.toString())
            //pass ip to pair fragment and navigate to it
        }
    }

    fun setData(newData: ArrayList<Mirror>) {
        mirrors = newData
        notifyDataSetChanged()
    }

    class MirrorHolder(v: View) : RecyclerView.ViewHolder(v){

        val mirrorName = v.findViewById(R.id.mirrorName) as (TextView)
        val mirrorImage = v.findViewById(R.id.mirrorImage) as (ImageView)


    }

}
