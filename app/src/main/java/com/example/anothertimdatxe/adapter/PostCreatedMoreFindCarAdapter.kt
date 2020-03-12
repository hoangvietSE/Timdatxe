package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post_created_find_car.*

class PostCreatedMoreFindCarAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup) = LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.loading, parent, false))

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup) = FindCarViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post_created_find_car, parent, false))

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data: UserPostResponse? = getItem(position, UserPostResponse::class.java)
        holder.bind(data!!)
    }

    class FindCarViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as UserPostResponse
            formTitle.text = data?.title
            formAvatar.setAvatar(itemView.context, data?.avatar)
            formStartingPoint.text = MapUtil.getLocationState(itemView.context, data?.lat_from?.toDouble()!!, data.lng_from?.toDouble()!!)
            formEndingPoint.text = MapUtil.getLocationState(itemView.context, data?.lat_to?.toDouble()!!, data?.lng_to?.toDouble()!!)
            formSeat.text = data.number_seat.toString()
            formTime.text = DateUtil.formatDate(data.start_time.toString(), DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
            formDate.text = DateUtil.formatDate(data.start_time.toString(), DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)

        }
    }
}