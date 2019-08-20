package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.item_add.view.*
import kotlinx.android.synthetic.main.item_car_image.view.*

class ImageAdapter(var context: Context, var mList: MutableList<DriverCarImage>, var mListener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_BTN_ADD = 0
        const val VIEW_TYPE_IMAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_BTN_ADD) {
            return AddButtonViewHolder(context.inflate(R.layout.item_add, parent, false))
        } else if (viewType == VIEW_TYPE_IMAGE) {
            return ImageViewHolder(context.inflate(R.layout.item_car_image, parent, false))
        }
        return ImageViewHolder(context.inflate(R.layout.item_default, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mList.get(position).type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (mList[position].type) {
            VIEW_TYPE_BTN_ADD -> {
                val addHolder = holder as AddButtonViewHolder
                if (addHolder.imvAdd != null) {
                    addHolder.imvAdd.setOnClickListener {
                        mListener.onItemAddClick()
                    }
                }

            }
            VIEW_TYPE_IMAGE -> {
                val imageHolder = holder as ImageViewHolder
                GlideApp.with(context)
                        .load(mList[position].uriImage)
                        .placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default)
                        .into(imageHolder.imvCar)
                if (imageHolder.btnCancel != null) {
                    imageHolder.btnCancel?.setOnClickListener {
                        mListener.onCancelClick(imageHolder.adapterPosition)
                    }
                }
            }
        }
    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnCancel: ImageView = itemView.btn_cancel
        val imvCar: ImageView = itemView.iv_car

    }

    class AddButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvAdd: ImageView = itemView.iv_add
    }

    fun addImage(image: DriverCarImage) {
        mList.add(image)
        notifyItemInserted(itemCount - 1)
    }

    fun addImageIndex(pos: Int, image: DriverCarImage) {
        mList.add(pos, image)
        notifyItemInserted(pos)
    }

    fun addListImage(list: List<DriverCarImage>) {
        val oldSize = mList.size
        mList.addAll(list)
        val newSize = mList.size
        notifyItemRangeInserted(oldSize, newSize - oldSize)
    }

    fun removeImageAt(position: Int): Boolean {
        mList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mList.size)
        if (position == 4) return true
        return false
    }

    fun getListSize(): Int {
        return mList.size
    }


}

interface OnClickListener {
    fun onCancelClick(position: Int)
    fun onItemAddClick()
}