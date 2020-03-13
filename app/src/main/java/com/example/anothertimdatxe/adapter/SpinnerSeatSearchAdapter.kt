package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate

class SpinnerSeatSearchAdapter(var context: Context, var data: List<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : TextView = context.inflate(context, R.layout.item_spinner_search) as TextView
        view.text = data[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : TextView = context.inflate(context, R.layout.item_spinner) as TextView
        view.text = data[position]
        return view
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}