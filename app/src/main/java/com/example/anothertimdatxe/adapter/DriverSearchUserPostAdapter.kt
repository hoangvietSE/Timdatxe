package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverSearchResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter

class DriverSearchUserPostAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return DriverSearchViewHolder(context!!.inflate(R.layout.item_driver_search, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val driverSearchHolder = holder as DriverSearchViewHolder
        val data = getItem(position, DriverSearchResponse::class.java)
        GlideApp.with(context!!)
                .load(data!!.avatar)
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(driverSearchHolder.imvAvatar)
        driverSearchHolder.tvTitle.text = data.title
        driverSearchHolder.tvStartingPoint.text = data.appStartProvince
        driverSearchHolder.tvEndingPoint.text = data.appEndProvince
        driverSearchHolder.tvNumberSeat.text = data.numberSeat!!.toString()
        driverSearchHolder.tvTime.text = DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
        driverSearchHolder.tvDate.text = DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        if (data.status == Constant.USER_POST_PUBLISHED) {
            holder.btnBook.visible()
        }
    }

    class DriverSearchViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val imvAvatar: ImageView = itemView.findViewById(R.id.imv_avatar)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvStartingPoint: TextView = itemView.findViewById(R.id.tv_starting_point)
        val tvEndingPoint: TextView = itemView.findViewById(R.id.tv_ending_point)
        val tvNumberSeat: TextView = itemView.findViewById(R.id.tv_number_seat)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val btnBook : TextView = itemView.findViewById(R.id.btn_recruitment)
    }
}