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
import com.example.anothertimdatxe.util.NumberUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_revenue_detail.*

class RevenueDetailAdapter(context: Context, var mListener: BaseRvListener) : BaseAdapter<DriverRevenueResponse, RevenueDetailAdapter.RevenueDetailViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RevenueDetailViewHolder(context.inflate(R.layout.item_revenue_detail, parent, false), mListener)

    class RevenueDetailViewHolder(override val containerView: View?, mListener: BaseRvListener) : BaseViewHolder<DriverRevenueResponse>(containerView!!, mListener), LayoutContainer {
        override fun bindData(data: DriverRevenueResponse) {
            tv_time.text = DateUtil.formatDate(data.start_date!!, DateUtil.DATE_FORMAT_21, DateUtil.DATE_FORMAT_23)
            tv_starting_point.text = data.start_point
            tv_ending_point.text = data.end_point
            tv_money.text = NumberUtil.formatNumber(data.total_money_trip.toString())
        }

    }
}