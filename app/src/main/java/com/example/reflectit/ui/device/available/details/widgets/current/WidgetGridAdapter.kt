import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reflectit.R
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetCategory
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder


class WidgetGridAdapter(val selectedWidgets: MutableList<Widget>, val context: Context?) :
    RecyclerView.Adapter<MyAdapter.Companion.MyViewHolder>(),
    DraggableItemAdapter<MyAdapter.Companion.MyViewHolder> {

    override fun getItemId(position: Int): Long {
        return selectedWidgets[position].id.toLong()
    }

    override fun onGetItemDraggableRange(holder: MyAdapter.Companion.MyViewHolder, position: Int): ItemDraggableRange? {
        return null
    }

    override fun onCheckCanStartDrag(holder: MyAdapter.Companion.MyViewHolder, position: Int, x: Int, y: Int): Boolean {
        val itemView = holder.itemView
        return holder.imageView.drawable != null
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        val widget = selectedWidgets.removeAt(fromPosition)
        selectedWidgets.add(toPosition, widget)
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return true
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Companion.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.widgets_position_row, parent, false)
        return MyAdapter.Companion.MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if(selectedWidgets.size <= 11) { selectedWidgets.size } else 11
    }

    override fun onBindViewHolder(holder: MyAdapter.Companion.MyViewHolder, position: Int) {

        if (holder.dragState.isUpdated) {
            val layoutId = when {
                holder.dragState.isActive -> 0
                holder.dragState.isDragging -> R.drawable.dragging_state

                else -> 0
            }

            holder.itemView.setBackgroundResource(layoutId)
        }

        if (selectedWidgets[position].category != WidgetCategory.Placeholder) {
            Glide.with(holder.imageView.context)
                .load(selectedWidgets[position].imageUrl)
                .into(holder.imageView)
//            Glide
//                .with(holder.imageView.context)
//                .load(selectedWidgets[position].imageUrl)
//                .fitCenter()
//                .placeholder(R.drawable.mirror_image)
//                .into(holder.imageView)
        }
    }

    init {
        setHasStableIds(true)
    }
}

class MyAdapter {
    companion object {
        class MyViewHolder(v: View) : AbstractDraggableItemViewHolder(v) {
            val imageView: ImageView
//            val dragHandle: View

            init {
                imageView = v.findViewById(R.id.widgetImageView)
//                dragHandle = v.findViewById(R.id.drag_handle)
            }

        }
    }
}