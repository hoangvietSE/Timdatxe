package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.entity.response.DriverRevenueResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.DateUtil
import kotlinx.android.synthetic.main.item_revenue_detail.view.*

class RevenueDetailAdapter(context: Context, var mListener: BaseRvListener) : BaseAdapter<DriverRevenueResponse, RevenueDetailAdapter.RevenueDetailViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevenueDetailViewHolder {
        return RevenueDetailViewHolder(context!!.inflate(R.layout.item_revenue_detail, parent, false), mListener)
    }

    class RevenueDetailViewHolder(itemView: View, mListener: BaseRvListener) : BaseViewHolder<DriverRevenueResponse>(itemView, mListener) {
        override fun bindData(data: DriverRevenueResponse) {
            itemView.tv_time.text = DateUtil.formatDate(data.start_date!!, DateUtil.DATE_FORMAT_21, DateUtil.DATE_FORMAT_23)
            itemView.tv_starting_point.text = data.start_point
            itemView.tv_ending_point.text = data.end_point
            itemView.tv_money.text = "${data.total_money_trip} VNĐ"
        }

    }
}