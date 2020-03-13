package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverCarBrandResponse

class SpinnerCarBrandAdapter(var context: Context, var mListBrand: MutableList<DriverCarBrandResponse>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = (getItem(position)).brand
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView?
                ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as TextView
        view.text = getItem(position).brand
        return view
    }

    override fun getItem(position: Int) = mListBrand[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = mListBrand.size
}