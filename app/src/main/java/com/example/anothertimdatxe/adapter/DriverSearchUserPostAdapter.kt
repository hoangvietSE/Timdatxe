package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverSearchResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.soict.hoangviet.baseproject.extension.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_driver_search.*

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
        val data = getItem(position, DriverSearchResponse::class.java)
        holder.bind(data!!)
    }

    class DriverSearchViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as DriverSearchResponse
            imv_avatar.loadImage(
                    itemView.context,
                    data.avatar,
                    R.drawable.ic_avatar,
                    R.drawable.ic_avatar)
            tv_title.text = data.title
            tv_starting_point.text = data.appStartProvince
            tv_ending_point.text = data.appEndProvince
            tv_number_seat.text = data.numberSeat!!.toString()
            tv_time.text = DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            tv_date.text = DateUtil.formatDate(data.startTime, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            when(data.status){
                Constant.USER_POST_PUBLISHED-> btn_recruitment.visible()
            }
        }
    }
}