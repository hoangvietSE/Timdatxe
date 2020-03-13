package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate

class SpinnerGenderAdapter(var context: Context, var mListItem: ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : TextView = context.inflate(R.layout.item_spinner, parent!!, false) as TextView
        view.text = mListItem[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : TextView = context.inflate(R.layout.item_spinner, parent!!, false) as TextView
        view.text = mListItem[position]
        return view
    }

    override fun getItem(position: Int) = mListItem[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = mListItem.size
}