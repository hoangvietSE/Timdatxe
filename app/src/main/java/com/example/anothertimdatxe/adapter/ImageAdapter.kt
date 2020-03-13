package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.extension.inflate
import com.soict.hoangviet.baseproject.extension.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_add.view.*
import kotlinx.android.synthetic.main.item_car_image.view.*

class ImageAdapter(var context: Context, var mList: MutableList<DriverCarImage>, var mListener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_BTN_ADD = 0
        const val VIEW_TYPE_IMAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BTN_ADD -> AddButtonViewHolder(context.inflate(R.layout.item_add, parent, false))
            VIEW_TYPE_IMAGE -> ImageViewHolder(context.inflate(R.layout.item_car_image, parent, false))
            else -> ImageViewHolder(context.inflate(R.layout.item_default, parent, false))
        }
    }

    override fun getItemCount() = mList.size

    override fun getItemViewType(position: Int) = mList.get(position).type

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
                imageHolder.imvCar.loadImage(
                        context,
                        mList[position].uriImage
                )
                if (imageHolder.btnCancel != null) {
                    imageHolder.btnCancel.setOnClickListener {
                        mListener.onCancelClick(imageHolder.adapterPosition)
                    }
                }
            }
        }
    }


    class ImageViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        val btnCancel: ImageView = itemView.btn_cancel
        val imvCar: ImageView = itemView.iv_car
    }

    class AddButtonViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
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

    fun getListSize() = mList.size
}

interface OnClickListener {
    fun onCancelClick(position: Int)
    fun onItemAddClick()
}