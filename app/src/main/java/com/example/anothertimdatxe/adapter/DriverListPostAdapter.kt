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
import com.example.anothertimdatxe.entity.response.DriverListPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_driver_list_post.*

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
        holder.bind(data!!)
    }

    class ViewHolder(override val containerView: View?) : RecyclerViewAdapter.NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as DriverListPostResponse
            GlideApp.with(itemView.context)
                    .load(CarBookingSharePreference.getUserData()?.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(formAvatar)
            formTitle.text = data?.title
            formStartingPoint.text = data?.app_start_province
            formEndingPoint.text = data?.app_end_province
            tv_seat.text = data?.empty_seat.toString()
            tv_status.text = data?.str_status
            formTime.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            formDate.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            when (data?.status) {
                Constant.DRIVER_POST_PENDING -> {
                    setTextColor(tv_status, R.color.color_pending)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                Constant.DRIVER_POST_PUBLISHED -> {
                    setTextColor(tv_status, R.color.colorPrimary)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                Constant.DRIVER_POST_FINISH -> {
                    setTextColor(tv_status, R.color.color_finish)
                    setImageStatus(imv_status, R.drawable.ic_status_finish)
                }
                Constant.DRIVER_POST_CANCEL -> {
                    setTextColor(tv_status, R.color.color_cancel)
                    setImageStatus(imv_status, R.drawable.ic_status_cancel)
                }
            }
            formHighWay.gone()
            when (data.type) {
                TYPE_CONVINENT, TYPE_BOTH -> {
                    formMoney.text = NumberUtil.formatNumber(data.regular_price!!)
                }
                TYPE_PRIVATE -> {
                    formMoney.text = NumberUtil.formatNumber(data.private_price_2!!)
                }
            }
        }

        fun setTextColor(tv: TextView, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextColor(containerView!!.context.resources.getColor(color, null))
            } else {
                tv.setTextColor(containerView!!.context.resources.getColor(color))
            }
        }

        fun setImageStatus(imageStatus: ImageView, drawableRes: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageStatus.setImageDrawable(containerView!!.context.resources.getDrawable(drawableRes, null))
            } else {
                imageStatus.setImageDrawable(containerView!!.context.resources.getDrawable(drawableRes))
            }
        }
    }
}