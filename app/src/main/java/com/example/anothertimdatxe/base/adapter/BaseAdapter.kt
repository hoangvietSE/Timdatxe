package com.example.anothertimdatxe.base.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(var context: Context) : RecyclerView.Adapter<VH>() {
    protected var mListItem: ArrayList<T> = ArrayList()

    override fun getItemCount(): Int {
        return mListItem.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(mListItem[position])
    }

    fun clear() {
        mListItem.clear()
        notifyDataSetChanged()
    }

    fun setListItems(list: List<T>) {
        mListItem.clear()
        mListItem.addAll(list)
        notifyDataSetChanged()
    }

    fun addListItems(list: List<T>?) {
        if (list != null && list.isNotEmpty()) {
            var oldSize: Int = mListItem.size
            mListItem.addAll(list)
            var newSize: Int = mListItem.size
            notifyItemRangeChanged(oldSize, newSize - oldSize)
        }
    }

    fun addListItems(array: Array<T>?) {
        if (array != null && array.isNotEmpty()) {
            var oldSize: Int = mListItem.size
            mListItem.addAll(array)
            var newSize: Int = mListItem.size
            notifyItemRangeChanged(oldSize, newSize - oldSize)
        }
    }

    operator fun get(position: Int): T {
        return mListItem.get(position)
    }

    fun add(element: T) {
        mListItem.add(element)
    }

    fun addAndNotify(element: T) {
        mListItem.add(element)
        notifyItemChanged(itemCount - 1)
    }

    fun add(index: Int, element: T) {
        mListItem.add(index, element)
    }

    fun removeAt(index: Int): T {
        return mListItem.removeAt(index)
    }

    fun removeAndNotify(index: Int): T {
        return mListItem.removeAt(index)
        notifyItemRemoved(index)
    }
}