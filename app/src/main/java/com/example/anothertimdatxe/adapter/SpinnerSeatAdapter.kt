package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anothertimdatxe.R

class SpinnerSeatAdapter(var context: Context, var data: MutableList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = getItem(position)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = getItem(position)
        return view
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addAll(item: List<String>) {
        data.addAll(item)
        notifyDataSetChanged()
    }
}