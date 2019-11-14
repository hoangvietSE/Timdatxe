package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverCarBrandDetailResponse

class SpinnerCarNameAdapter(var context: Context, var mListName: MutableList<DriverCarBrandDetailResponse>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = (getItem(position)).name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = getItem(position).name
        return view
    }

    override fun getItem(position: Int): DriverCarBrandDetailResponse {
        return mListName[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mListName.size
    }

    fun clear() {
        mListName.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: DriverCarBrandDetailResponse) {
        mListName.add(item)
        notifyDataSetChanged()
    }
}