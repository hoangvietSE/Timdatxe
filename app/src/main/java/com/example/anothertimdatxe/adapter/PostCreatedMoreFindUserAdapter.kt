package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post_created_find_user.*

class PostCreatedMoreFindUserAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return FindUserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post_created_find_user, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data: DriverPostResponse? = getItem(position, DriverPostResponse::class.java)
        holder.bind(data!!)
    }

    class FindUserViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as DriverPostResponse
            formTitle.text = data?.title
            formAvatar.setAvatar(itemView.context, data?.avatar)
            tv_seat.text = data?.empty_seat.toString()
            formStartingPoint.text = MapUtil.getLocationState(itemView.context, data?.lat_from!!.toDouble(), data.lng_from!!.toDouble())
            formEndingPoint.text = MapUtil.getLocationState(itemView.context, data.lat_to!!.toDouble(), data.lng_to!!.toDouble())
            formTime.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
            formDate.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            if (data.regularPrice != null && data.private_price_1 == null) {
                formMoney.text = NumberUtil.formatNumber(data.regularPrice) + "/Người"
            } else if (data.regularPrice == null || (data.regularPrice != null && data.private_price_1 != null)) {
                formMoney.text = NumberUtil.formatNumber(data.private_price_1!!) + "/Người"
            }
            when (data.type) {
                Constant.CONVENIENT_TRIP -> {
                    formXeRieng.visible()
                    formXeRieng.gone()
                }
                Constant.PRIVATE_TRIP -> {
                    formXeRieng.gone()
                    formXeRieng.visible()
                }
                Constant.BOTH_CONVENIENT_AND_PRIVATE -> {
                    formXeRieng.visible()
                    formXeRieng.visible()
                }
            }
            if (data.high_way == 0) {
                formHighWay.gone()
            } else {
                formHighWay.visible()
            }
            if (CarBookingSharePreference.getUserData()?.isDriver!!) {
                btn_book.gone()
            }

        }
    }
}