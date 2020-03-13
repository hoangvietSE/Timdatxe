package com.example.anothertimdatxe.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.ListUserBookResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_list_post.*

class UserListBookAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return UserBookViewHolder(context!!.inflate(R.layout.item_user_list_post))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data = getItem(position, ListUserBookResponse::class.java)
        holder.bind(data!!)
    }

    class UserBookViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as ListUserBookResponse
            formTitle.text = data?.title
            formStartingPoint.text = data?.appStartProvince
            formEndingPoint.text = data?.appEndProvince
            tv_seat.text = data?.numberSeat.toString()
            tv_status.text = data?.strStatus
            formTime.text = DateUtil.formatDate(data?.bookTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            formDate.text = DateUtil.formatDate(data?.bookTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            when (data?.status) {
                Constant.USER_BOOK_PENDING -> {
                    imv_status.setImageResource(R.drawable.ic_status_pending)
                    setColorStatus(tv_status, R.color.color_pending)
                }
                Constant.USER_BOOK_DONE -> {
                    imv_status.setImageResource(R.drawable.ic_status)
                    setColorStatus(tv_status, R.color.colorPrimary)
                }
                Constant.USER_BOOK_FINISH -> {
                    imv_status.setImageResource(R.drawable.ic_status_finish)
                    setColorStatus(tv_status, R.color.color_finish)
                }
                Constant.USER_BOOK_CANCEL -> {
                    imv_status.setImageResource(R.drawable.ic_status_cancel)
                    setColorStatus(tv_status, R.color.color_cancel)
                }
            }

        }
        private fun setColorStatus(tvStatus: TextView, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStatus.setTextColor(itemView.context.resources.getColor(color, null))
            } else {
                tvStatus.setTextColor(itemView.context.resources.getColor(color))
            }
        }
    }

}