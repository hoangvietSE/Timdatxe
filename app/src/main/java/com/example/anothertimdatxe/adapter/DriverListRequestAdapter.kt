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
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_driver_list_request.*

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
        holder.bind(data!!)
    }

    class ViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as DriverListRequestResponse
            GlideApp.with(itemView.context!!)
                    .load(data?.user?.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(imv_avatar)
            tv_title.text = data?.userPost?.title
            tv_starting_point.text = data?.userPost?.app_start_province
            tv_ending_point.text = data?.userPost?.app_end_province
            tv_number_seat.text = data?.userPost?.numberSeat.toString()
            tv_status.text = data?.strStatus
            tv_time.text = DateUtil.formatDate(data?.userPost?.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            tv_date.text = DateUtil.formatDate(data.userPost?.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            when (data?.status) {
                Constant.DRIVER_BOOK_PENDING -> {
                    setTextColor(tv_status, R.color.color_pending)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                Constant.DRIVER_BOOK_ACCEPTED -> {
                    setTextColor(tv_status, R.color.colorPrimary)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                Constant.DRIVER_BOOK_REJECTED -> {
                    setTextColor(tv_status, R.color.color_reject)
                    setImageStatus(imv_status, R.drawable.ic_status_reject)
                }
                Constant.DRIVER_BOOK_FINISH -> {
                    setTextColor(tv_status, R.color.color_finish)
                    setImageStatus(imv_status, R.drawable.ic_status_finish)
                }
                Constant.DRIVER_BOOK_CANCEL -> {
                    setTextColor(tv_status, R.color.color_cancel)
                    setImageStatus(imv_status, R.drawable.ic_status_cancel)
                }
            }
        }

        fun setTextColor(tv: TextView, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextColor(itemView.context!!.resources.getColor(color, null))
            } else {
                tv.setTextColor(itemView.context!!.resources.getColor(color))
            }
        }

        fun setImageStatus(imageStatus: ImageView, drawableRes: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageStatus.setImageDrawable(itemView.context!!.resources.getDrawable(drawableRes, null))
            } else {
                imageStatus.setImageDrawable(itemView.context!!.resources.getDrawable(drawableRes))
            }
        }
    }
}