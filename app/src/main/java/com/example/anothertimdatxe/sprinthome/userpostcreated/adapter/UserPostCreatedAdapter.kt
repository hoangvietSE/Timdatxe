package com.example.anothertimdatxe.sprinthome.userpostcreated.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.entity.UserListPostEntity
import kotlinx.android.synthetic.main.item_row_post_created.view.*

class UserPostCreatedAdapter(context: Context, var mListener: UserPostCreatedListener) : BaseAdapter<UserListPostEntity, UserPostCreatedAdapter.UserPostCreatedViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostCreatedViewHolder {
        var inflater = LayoutInflater.from(context)
        return UserPostCreatedViewHolder(inflater.inflate(R.layout.item_row_post_created, parent, false), mListener)
    }

    inner class UserPostCreatedViewHolder(itemView: View, mListener: UserPostCreatedListener) : BaseViewHolder<UserListPostEntity>(itemView, mListener) {
        override fun bindData(data: UserListPostEntity) {
            //itemView.tv_starting_point.text = MapUtil.getLocationState(context, data.lat_from!!.toDouble(), data.lng_from!!.toDouble())
            //itemView.tv_ending_point.text = MapUtil.getLocationState(context, data.lat_to!!.toDouble(), data.lng_to!!.toDouble())
            itemView.tv_number_seat.text = data.number_seat.toString()
            itemView.tv_status.text = data.str_status
        }

    }
}