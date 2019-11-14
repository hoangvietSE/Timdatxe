package com.example.anothertimdatxe.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverListRequestResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil

class DriverListRequestAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return ViewHolder(context!!.inflate(R.layout.item_driver_list_request, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data = getItem(position, DriverListRequestResponse::class.java)
        val viewHolder = holder as ViewHolder
        GlideApp.with(context!!)
                .load(data?.user?.avatar)
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(viewHolder.avatar)
        viewHolder.title.text = data?.userPost?.title
        viewHolder.startingPoint.text = data?.userPost?.app_start_province
        viewHolder.endingPoint.text = data?.userPost?.app_end_province
        viewHolder.numberSeat.text = data?.userPost?.numberSeat.toString()
        viewHolder.status.text = data?.strStatus
        viewHolder.time.text = DateUtil.formatDate(data?.userPost?.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
        viewHolder.date.text = DateUtil.formatDate(data.userPost?.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        when (data?.status) {
            Constant.DRIVER_BOOK_PENDING -> {
                setTextColor(viewHolder.status, R.color.color_pending)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status)
            }
            Constant.DRIVER_BOOK_ACCEPTED -> {
                setTextColor(viewHolder.status, R.color.colorPrimary)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status)
            }
            Constant.DRIVER_BOOK_REJECTED -> {
                setTextColor(viewHolder.status, R.color.color_reject)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status_reject)
            }
            Constant.DRIVER_BOOK_FINISH -> {
                setTextColor(viewHolder.status, R.color.color_finish)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status_finish)
            }
            Constant.DRIVER_BOOK_CANCEL -> {
                setTextColor(viewHolder.status, R.color.color_cancel)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status_cancel)
            }
        }
    }

    fun setTextColor(tv: TextView, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextColor(context!!.resources.getColor(color, null))
        } else {
            tv.setTextColor(context!!.resources.getColor(color))
        }
    }

    fun setImageStatus(imageStatus: ImageView, drawableRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageStatus.setImageDrawable(context!!.resources.getDrawable(drawableRes, null))
        } else {
            imageStatus.setImageDrawable(context!!.resources.getDrawable(drawableRes))
        }
    }

    class ViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val avatar: ImageView = itemView.findViewById(R.id.imv_avatar)
        val startingPoint: TextView = itemView.findViewById(R.id.tv_starting_point)
        val endingPoint: TextView = itemView.findViewById(R.id.tv_ending_point)
        val numberSeat: TextView = itemView.findViewById(R.id.tv_number_seat)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val imvStatus: ImageView = itemView.findViewById(R.id.imv_status)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }
}