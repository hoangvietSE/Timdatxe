package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverHistoryResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history_travel.*

class DriverHistoryAdapter(context: Context, enableSelectedMode: Boolean) : EndlessLoadingRecyclerViewAdapter(context, enableSelectedMode) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return DriverHistoryViewHolder(context!!.inflate(R.layout.item_history_travel, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data: DriverHistoryResponse? = getItem(position, DriverHistoryResponse::class.java)
        holder.bind(data!!)
    }

    class DriverHistoryViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as DriverHistoryResponse
            formStartingPoint.text = MapUtil.getLocationState(itemView.context, data?.lat_from!!.toDouble(), data.lng_from!!.toDouble())
            formEndingPoint.text = MapUtil.getLocationState(itemView.context, data.lat_to!!.toDouble(), data?.lng_to!!.toDouble())
            layout_seat.gone()
            formTime.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
            formDate.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            formAvatar.setAvatar(itemView.context, data?.avatar)
            formTitle.text = data?.title
            formSeat.text = data?.seat.toString()
        }
    }
}