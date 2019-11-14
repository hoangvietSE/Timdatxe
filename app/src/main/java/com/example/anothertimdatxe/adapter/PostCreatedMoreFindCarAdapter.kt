package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil

class PostCreatedMoreFindCarAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return FindCarViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post_created_find_car, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data: UserPostResponse? = getItem(position, UserPostResponse::class.java)
        val mHolder: FindCarViewHolder = holder as FindCarViewHolder
        mHolder.title.text = data?.title
        mHolder.avatar.setAvatar(context!!, data?.avatar)
        mHolder.start_point.text = MapUtil.getLocationState(context!!, data?.lat_from?.toDouble()!!, data.lng_from?.toDouble()!!)
        mHolder.end_point.text = MapUtil.getLocationState(context!!, data?.lat_to?.toDouble()!!, data?.lng_to?.toDouble()!!)
        mHolder.seat.text = data!!.number_seat.toString()
        mHolder.time.text = DateUtil.formatDate(data!!.start_time.toString(), DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
        mHolder.date.text = DateUtil.formatDate(data!!.start_time.toString(), DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
    }

    class FindCarViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.formTitle)
        val avatar: ImageView = itemView.findViewById(R.id.formAvatar)
        val start_point: TextView = itemView.findViewById(R.id.formStartingPoint)
        val end_point: TextView = itemView.findViewById(R.id.formEndingPoint)
        val seat: TextView = itemView.findViewById(R.id.formSeat)
        val time: TextView = itemView.findViewById(R.id.formTime)
        val date: TextView = itemView.findViewById(R.id.formDate)
    }
}