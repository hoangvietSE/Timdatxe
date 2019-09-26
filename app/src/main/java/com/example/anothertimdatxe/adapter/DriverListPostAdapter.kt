package com.example.anothertimdatxe.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverListPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter

class DriverListPostAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    companion object {
        const val TYPE_CONVINENT = 0
        const val TYPE_PRIVATE = 1
        const val TYPE_BOTH = 2
    }

    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return ViewHolder(context!!.inflate(R.layout.item_driver_list_post, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data = getItem(position, DriverListPostResponse::class.java)
        val viewHolder = holder as ViewHolder
        GlideApp.with(context!!)
                .load(CarBookingSharePreference.getUserData()?.avatar)
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(viewHolder.avatar)
        viewHolder.title.text = data?.title
        viewHolder.startingPoint.text = data?.app_start_province
        viewHolder.endingPoint.text = data?.app_end_province
        viewHolder.numberSeat.text = data?.empty_seat.toString()
        viewHolder.status.text = data?.str_status
        viewHolder.time.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
        viewHolder.date.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        when (data?.status) {
            Constant.DRIVER_POST_PENDING -> {
                setTextColor(viewHolder.status, R.color.color_pending)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status)
            }
            Constant.DRIVER_POST_PUBLISHED -> {
                setTextColor(viewHolder.status, R.color.colorPrimary)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status)
            }
            Constant.DRIVER_POST_FINISH -> {
                setTextColor(viewHolder.status, R.color.color_finish)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status_finish)
            }
            Constant.DRIVER_POST_CANCEL -> {
                setTextColor(viewHolder.status, R.color.color_cancel)
                setImageStatus(viewHolder.imvStatus, R.drawable.ic_status_cancel)
            }
        }
        if (data.high_way == 0) {
            viewHolder.formHighway.gone()
        } else {
            viewHolder.formHighway.visible()
        }
        when (data.type) {
            TYPE_CONVINENT, TYPE_BOTH -> {
                viewHolder.money.text = NumberUtil.formatNumber(data.regular_price!!)
            }
            TYPE_PRIVATE -> {
                viewHolder.money.text = NumberUtil.formatNumber(data.private_price_2!!)
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

    class ViewHolder(itemView: View) : RecyclerViewAdapter.NormalViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.formTitle)
        val avatar: ImageView = itemView.findViewById(R.id.formAvatar)
        val startingPoint: TextView = itemView.findViewById(R.id.formStartingPoint)
        val endingPoint: TextView = itemView.findViewById(R.id.formEndingPoint)
        val formHighway: LinearLayout = itemView.findViewById(R.id.formHighWay)
        val numberSeat: TextView = itemView.findViewById(R.id.tv_seat)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val imvStatus: ImageView = itemView.findViewById(R.id.imv_status)
        val time: TextView = itemView.findViewById(R.id.formTime)
        val date: TextView = itemView.findViewById(R.id.formDate)
        val money: TextView = itemView.findViewById(R.id.formMoney)
    }
}