package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.item_hot_cities.view.*

class HotCitiesAdapter(context: Context, var mListener: BaseRvListener) : BaseAdapter<HotCitiesResponse, HotCitiesAdapter.HotCitiesViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCitiesViewHolder {
        return HotCitiesViewHolder(context!!.inflate(R.layout.item_hot_cities, parent, false), mListener)
    }

    class HotCitiesViewHolder(itemView: View, mListener: BaseRvListener) : BaseViewHolder<HotCitiesResponse>(itemView, mListener) {
        override fun bindData(data: HotCitiesResponse) {
            GlideApp.with(itemView)
                    .load(data.app_image)
                    .override(80, 120)
                    .into(itemView.imv_hot_cities)
        }

    }
}