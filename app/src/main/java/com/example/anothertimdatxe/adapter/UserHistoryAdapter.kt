package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.UserHistoryResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter

class UserHistoryAdapter(context: Context, enableSelectedMode: Boolean) : EndlessLoadingRecyclerViewAdapter(context, enableSelectedMode) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return UserHistoryViewHolder(context!!.inflate(R.layout.item_history_travel, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data: UserHistoryResponse? = getItem(position, UserHistoryResponse::class.java)
        val viewHolder: UserHistoryViewHolder = holder as UserHistoryViewHolder
        viewHolder.start_point.text = MapUtil.getLocationState(context!!, data?.lat_from!!.toDouble(), data?.lng_from!!.toDouble())
        viewHolder.end_point.text = MapUtil.getLocationState(context!!, data?.lat_to!!.toDouble(), data?.lng_to!!.toDouble())
        viewHolder.money.text = data?.total_price
        viewHolder.time.text = DateUtil.formatDate(data?.book_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
        viewHolder.date.text = DateUtil.formatDate(data?.book_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        viewHolder.imv_avatar.setAvatar(context!!, data?.avatar)
        viewHolder.title.text = data?.title
        viewHolder.number_seat.text = data?.number_seat!!.toString()
    }

    class UserHistoryViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val start_point: TextView = itemView.findViewById(R.id.formStartingPoint)
        val end_point: TextView = itemView.findViewById(R.id.formEndingPoint)
        val number_seat: TextView = itemView.findViewById(R.id.formSeat)
        val time: TextView = itemView.findViewById(R.id.formTime)
        val date: TextView = itemView.findViewById(R.id.formDate)
        val money: TextView = itemView.findViewById(R.id.formMoney)
        val imv_avatar: ImageView = itemView.findViewById(R.id.formAvatar)
        val title: TextView = itemView.findViewById(R.id.formTitle)

    }
}