package com.example.anothertimdatxe.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T> : RecyclerView.ViewHolder {
    constructor(itemView: View, mListener: BaseRvListener?) : super(itemView) {
        itemView.let {
            it.setOnClickListener {
                mListener!!.onItemClick(adapterPosition)
            }
        }
    }

    abstract fun bindData(data: T)
}