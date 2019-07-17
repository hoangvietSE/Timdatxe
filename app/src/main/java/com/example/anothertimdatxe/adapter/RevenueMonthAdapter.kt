package com.example.anothertimdatxe.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.sprinthome.revenue.item.ItemMonth
import kotlinx.android.synthetic.main.item_revenue_month.view.*

class RevenueMonthAdapter(context: Context, var mListener: BaseRvListener) : com.example.anothertimdatxe.base.adapter.BaseAdapter<ItemMonth, RevenueMonthAdapter.RevenueMonthViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevenueMonthViewHolder {
        return RevenueMonthViewHolder(context.inflate(R.layout.item_revenue_month, parent, false), mListener)
    }

    class RevenueMonthViewHolder(itemView: View, mListener: BaseRvListener) : BaseViewHolder<ItemMonth>(itemView, mListener) {
        override fun bindData(data: ItemMonth) {
            itemView.tv_month.text = data.month
            if (!data.isSelected) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    itemView.tv_month.setTextColor(itemView.resources.getColor(R.color.white, null))
                } else {
                    itemView.tv_month.setTextColor(itemView.resources.getColor(R.color.white))
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemView.background = itemView.resources.getDrawable(R.drawable.bg_item_month,null)
                } else {
                    itemView.background = itemView.resources.getDrawable(R.drawable.bg_item_month)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    itemView.tv_month.setTextColor(itemView.resources.getColor(R.color.black, null))
                } else {
                    itemView.tv_month.setTextColor(itemView.resources.getColor(R.color.black))
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemView.background = itemView.resources.getDrawable(R.drawable.bg_item_selected,null)
                } else {
                    itemView.background = itemView.resources.getDrawable(R.drawable.bg_item_selected)
                }
            }
        }

    }
}