package com.example.anothertimdatxe.adapter

import androidx.recyclerview.widget.DiffUtil


class DiffUtilCallBack(
        private val oldItems: List<RecyclerViewAdapter.ModelWrapper>,
        private val newItems: List<RecyclerViewAdapter.ModelWrapper>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition].id == newItems.get(newItemPosition).id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition] == newItems[newItemPosition]
}
