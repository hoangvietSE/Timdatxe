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
import com.example.anothertimdatxe.entity.response.SearchCityPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MyApp
import com.example.anothertimdatxe.util.NumberUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search_city_post.*

class SearchCityPostAdapter(context: Context, var key: String) : EndlessLoadingRecyclerViewAdapter(context, false) {
    private var dataUser: SearchCityPostResponse? = null
    private var dataDriver: SearchCityPostResponse? = null

    companion object {
        const val KEY_DRIVER_SEARCH = "driver"
        const val KEY_USER_SEARCH = "user"

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
        when (key) {
            KEY_DRIVER_SEARCH -> {
                dataUser = getItem(position, SearchCityPostResponse::class.java)
                cityPostHolder.bindDataDriverSearch(dataUser!!)
            }
            KEY_USER_SEARCH -> {
                dataDriver = getItem(position, SearchCityPostResponse::class.java)
                cityPostHolder.bindDataUserSearch(dataDriver!!)
            }
        }
    }

    class SearchCityPostViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        fun bindDataDriverSearch(dataUser: SearchCityPostResponse) {
            tv_title.text = dataUser.title
            GlideApp.with(itemView.context)
                    .load(dataUser.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(imv_avatar)
            tv_starting_point.text = dataUser.appStartProvince
            tv_ending_point.text = dataUser.appEndProvince
            tv_number_seat.text = dataUser.numberSeat.toString()
            tv_status.text = dataUser.str_status
            tv_time.text = DateUtil.formatDate(dataUser.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            tv_date.text = DateUtil.formatDate(dataUser.startTime, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            formMoney.gone()
            if (CarBookingSharePreference.getUserData()?.isDriver!!) {
                btn_recruitment.gone()
            }
            when (dataUser!!.status) {
                MyApp.KEY_PENDING -> {
                    setColorStatus(tv_status, R.color.color_pending)
                    setImageStatus(imv_status, R.drawable.ic_status)

                }
                MyApp.KEY_PUBLISHED -> {
                    setColorStatus(tv_status, R.color.colorPrimary)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                MyApp.KEY_DONE -> {
                    setColorStatus(tv_status, R.color.color_reject)
                    setImageStatus(imv_status, R.drawable.ic_status_reject)
                }
                MyApp.KEY_FINISH -> {
                    setColorStatus(tv_status, R.color.color_finish)
                    setImageStatus(imv_status, R.drawable.ic_status_finish)
                }
                MyApp.KEY_CANCEL -> {
                    setColorStatus(tv_status, R.color.color_cancel)
                    setImageStatus(imv_status, R.drawable.ic_status_cancel)
                }
            }
        }

        fun bindDataUserSearch(dataDriver: SearchCityPostResponse) {
            tv_title.text = dataDriver.title
            GlideApp.with(itemView.context)
                    .load(dataDriver.avatar)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(imv_avatar)
            tv_starting_point.text = dataDriver!!.appStartProvince
            tv_ending_point.text = dataDriver!!.appEndProvince
            tv_number_seat.text = dataDriver!!.emptySeat.toString()
            tv_status.text = dataDriver!!.str_status
            tv_time.text = DateUtil.formatDate(dataDriver!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            tv_date.text = DateUtil.formatDate(dataDriver!!.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            formMoney.visible()
            when (dataDriver!!.type) {
                MyApp.KEY_CONVENIENT_CAR -> {
                    tv_money.text = "${NumberUtil.formatNumber(dataDriver!!.regularPrice!!)}/Người"
                }
                MyApp.KEY_PRIVATE_CAR -> {
                    tv_money.text = NumberUtil.formatNumber(dataDriver!!.privatePrice2!!)
                }
                MyApp.KEY_BOTH_CAR -> {
                    tv_money.text = NumberUtil.formatNumber(dataDriver!!.privatePrice2!!)
                }
            }
            when (dataDriver!!.status) {
                KEY_DRIVER_POST_PENDING -> {
                    setColorStatus(tv_status, R.color.color_pending)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                KEY_DRIVER_POST_PUBLISHED -> {
                    setColorStatus(tv_status, R.color.colorPrimary)
                    setImageStatus(imv_status, R.drawable.ic_status)
                }
                KEY_DRIVER_POST_FINISH -> {
                    setColorStatus(tv_status, R.color.color_finish)
                    setImageStatus(imv_status, R.drawable.ic_status_finish)
                }
                KEY_DRIVER_POST_CANCEL -> {
                    setColorStatus(tv_status, R.color.color_cancel)
                    setImageStatus(imv_status, R.drawable.ic_status_cancel)
                }
            }
        }

        private fun setImageStatus(imageStatus: ImageView, drawableRes: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageStatus.setImageDrawable(itemView.context.resources.getDrawable(drawableRes, null))
            } else {
                imageStatus.setImageDrawable(itemView.context.resources.getDrawable(drawableRes))
            }
        }

        private fun setColorStatus(tvStatus: TextView, colorRes: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStatus.setTextColor(itemView.context.resources.getColor(colorRes, null))
            } else {
                tvStatus.setTextColor(itemView.context.resources.getColor(colorRes))
            }
        }
    }
}