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
import com.example.anothertimdatxe.entity.response.DriverSearchCityPostResponse
import com.example.anothertimdatxe.entity.response.UserSearchCityPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MyApp
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter

class SearchCityPostAdapter(context: Context, var key: String) : EndlessLoadingRecyclerViewAdapter(context, false) {
    private var dataUser: UserSearchCityPostResponse? = null
    private var dataDriver: DriverSearchCityPostResponse? = null

    companion object {
        const val KEY_DRIVER = "driver"
        const val KEY_USER = "user"

        //status
        const val KEY_DRIVER_POST_PENDING = 0
        const val KEY_DRIVER_POST_PUBLISHED = 1
        const val KEY_DRIVER_POST_FINISH = 2
        const val KEY_DRIVER_POST_CANCEL = 3
    }

    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return SearchCityPostViewHolder(context!!.inflate(R.layout.item_search_city_post, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val cityPostHolder: SearchCityPostViewHolder = holder as SearchCityPostViewHolder
        if (key == KEY_DRIVER) {
            dataDriver = getItem(position, DriverSearchCityPostResponse::class.java)
            cityPostHolder.tvTitle.text = dataDriver!!.title
            GlideApp.with(context!!)
                    .load(dataDriver!!.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(cityPostHolder.imvAvatar)
            cityPostHolder.tvStartingPoint.text = dataDriver!!.appStartProvince
            cityPostHolder.tvEndingPoint.text = dataDriver!!.appEndProvince
            cityPostHolder.tvNumberSeat.text = dataDriver!!.emptySeat.toString()
            cityPostHolder.tvStatus.text = dataDriver!!.str_status
            cityPostHolder.tvTime.text = DateUtil.formatDate(dataDriver!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            cityPostHolder.tvDate.text = DateUtil.formatDate(dataDriver!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            cityPostHolder.tvMoney.visible()
            when (dataDriver!!.type) {
                MyApp.KEY_CONVENIENT_CAR -> {
                    cityPostHolder.tvMoney.text = "${dataDriver!!.regularPrice}/Người"
                }
                MyApp.KEY_PRIVATE_CAR -> {
                    cityPostHolder.tvMoney.text = dataDriver!!.priceLevel2
                }
                MyApp.KEY_BOTH_CAR -> {
                    cityPostHolder.tvMoney.text = dataDriver!!.priceLevel2
                }
            }
            when (dataDriver!!.status) {
                KEY_DRIVER_POST_PENDING -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_pending)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status)
                }
                KEY_DRIVER_POST_PUBLISHED -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.colorPrimary)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status)
                }
                KEY_DRIVER_POST_FINISH -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_finish)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status_finish)
                }
                KEY_DRIVER_POST_CANCEL -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_cancel)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status_cancel)
                }
            }
        } else if (key == KEY_USER) {
            dataUser = getItem(position, UserSearchCityPostResponse::class.java)
            cityPostHolder.tvTitle.text = dataUser!!.title
            GlideApp.with(context!!)
                    .load(dataUser!!.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(cityPostHolder.imvAvatar)
            cityPostHolder.tvStartingPoint.text = dataUser!!.appStartProvince
            cityPostHolder.tvEndingPoint.text = dataUser!!.appEndProvince
            cityPostHolder.tvNumberSeat.text = dataUser!!.numberSeat.toString()
            cityPostHolder.tvStatus.text = dataUser!!.str_status
            cityPostHolder.tvTime.text = DateUtil.formatDate(dataUser!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            cityPostHolder.tvDate.text = DateUtil.formatDate(dataUser!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            cityPostHolder.tvMoney.gone()
            when (dataUser!!.status) {
                MyApp.KEY_PENDING -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_pending)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status)
                }
                MyApp.KEY_PUBLISHED -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.colorPrimary)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status)
                }
                MyApp.KEY_DONE -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_reject)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status_reject)
                }
                MyApp.KEY_FINISH -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_finish)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status_finish)
                }
                MyApp.KEY_CANCEL -> {
                    setColorStatus(cityPostHolder.tvStatus, R.color.color_cancel)
                    setImageStatus(cityPostHolder.imageStatus, R.drawable.ic_status_cancel)
                }
            }
        }
    }

    fun setImageStatus(imageStatus: ImageView, drawableRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageStatus.setImageDrawable(context!!.resources.getDrawable(drawableRes, null))
        } else {
            imageStatus.setImageDrawable(context!!.resources.getDrawable(drawableRes))
        }
    }

    fun setColorStatus(tvStatus: TextView, colorRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvStatus.setTextColor(context!!.resources.getColor(colorRes, null))
        } else {
            tvStatus.setTextColor(context!!.resources.getColor(colorRes))
        }
    }

    class SearchCityPostViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val imvAvatar: ImageView = itemView.findViewById(R.id.imv_avatar)
        val tvStartingPoint: TextView = itemView.findViewById(R.id.tv_starting_point)
        val tvEndingPoint: TextView = itemView.findViewById(R.id.tv_ending_point)
        val tvNumberSeat: TextView = itemView.findViewById(R.id.tv_number_seat)
        val imageStatus: ImageView = itemView.findViewById(R.id.imv_status)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvMoney: TextView = itemView.findViewById(R.id.tv_money)
    }
}