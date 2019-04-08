package com.example.reflectit.ui.device.available.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.example.reflectit.ui.data.models.Mirror

class MirrorAdapter(private var mirrors: ArrayList<Mirror>) : RecyclerView.Adapter<MirrorAdapter.MirrorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MirrorHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.mirror_item, parent, false)
        return MirrorHolder(inflatedView)
    }

    override fun getItemCount() = mirrors.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MirrorHolder, position: Int) {
        val mirror: Mirror = mirrors[position]
        holder.mirrorName.setText(R.string.defaultMirrorName)
        holder.mirrorImage.setImageResource(R.drawable.mirror_image)

        holder.itemView.setOnClickListener(View.OnClickListener {

            // pass ip to pair fragment and navigate to it
            val navAction = AvailableDevicesViewDirections.actionPair(mirror.ip.toString(), mirror.port.toString())
            Navigation.findNavController(it).navigate(navAction)
        })
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
