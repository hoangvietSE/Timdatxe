package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil
import com.example.anothertimdatxe.util.NumberUtil

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
        val mHolder: FindUserViewHolder = holder as FindUserViewHolder
        mHolder.title.text = data?.title
        mHolder.avatar.setAvatar(context!!, data?.avatar)
        mHolder.seat.text = data?.empty_seat.toString()
        mHolder.start_point.text = MapUtil.getLocationState(context!!, data?.lat_from!!.toDouble(), data.lng_from!!.toDouble())
        mHolder.end_point.text = MapUtil.getLocationState(context!!, data.lat_to!!.toDouble(), data.lng_to!!.toDouble())
        mHolder.time.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_7)
        mHolder.date.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        if (data.regularPrice != null && data.private_price_1 == null) {
            mHolder.money.text = NumberUtil.formatNumber(data.regularPrice) + "/Người"
        } else if (data.regularPrice == null || (data.regularPrice != null && data.private_price_1 != null)) {
            mHolder.money.text = NumberUtil.formatNumber(data.private_price_1!!) + "/Người"
        }
        when (data.type) {
            Constant.CONVENIENT_TRIP -> {
                mHolder.formTienChuyen.visible()
                mHolder.formXeRieng.gone()
            }
            Constant.PRIVATE_TRIP -> {
                mHolder.formTienChuyen.gone()
                mHolder.formXeRieng.visible()
            }
            Constant.BOTH_CONVENIENT_AND_PRIVATE -> {
                mHolder.formTienChuyen.visible()
                mHolder.formXeRieng.visible()
            }
        }
        if (data.high_way == 0) {
            mHolder.formHightWay.gone()
        } else {
            mHolder.formHightWay.visible()
        }

    }

    class FindUserViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.formTitle)
        val avatar: ImageView = itemView.findViewById(R.id.formAvatar)
        val start_point: TextView = itemView.findViewById(R.id.formStartingPoint)
        val end_point: TextView = itemView.findViewById(R.id.formEndingPoint)
        val seat : TextView = itemView.findViewById(R.id.tv_seat)
        val time: TextView = itemView.findViewById(R.id.formTime)
        val date: TextView = itemView.findViewById(R.id.formDate)
        val money: TextView = itemView.findViewById(R.id.formMoney)
        val formHightWay: LinearLayout = itemView.findViewById(R.id.formHighWay)
        val formXeRieng: LinearLayout = itemView.findViewById(R.id.formXeRieng)
        val formTienChuyen: LinearLayout = itemView.findViewById(R.id.formTienChuyen)
        val btnBook : TextView = itemView.findViewById(R.id.btn_book)
    }
}