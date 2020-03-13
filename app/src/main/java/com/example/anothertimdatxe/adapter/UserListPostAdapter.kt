package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_list_post.*

class UserListPostAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return UserPostViewHolder(context!!.inflate(R.layout.item_user_list_post))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data = getItem(position, UserListPostEntity::class.java)
        holder.bind(data!!)
    }

    class UserPostViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as UserListPostEntity
            formTitle.text = data.title
            formStartingPoint.text = data.app_start_province
            formEndingPoint.text = data.app_end_province
            tv_seat.text = data.number_seat.toString()
            tv_status.text = data.str_status
            formTime.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
            formDate.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            when (data.status) {
                Constant.USER_POST_PENDING -> {
                    imv_status.setImageResource(R.drawable.ic_status_pending)
                    setColorStatus(tv_status, R.color.color_pending)
                }
                Constant.USER_POST_PUBLISHED -> {
                    imv_status.setImageResource(R.drawable.ic_status)
                    setColorStatus(tv_status, R.color.colorPrimary)
                }
                Constant.USER_POST_DONE -> {
                    imv_status.setImageResource(R.drawable.ic_status_reject)
                    setColorStatus(tv_status, R.color.color_reject)
                }
                Constant.USER_POST_FINISH -> {
                    imv_status.setImageResource(R.drawable.ic_status_finish)
                    setColorStatus(tv_status, R.color.color_finish)
                }
                Constant.USER_POST_CANCEL -> {
                    imv_status.setImageResource(R.drawable.ic_status_cancel)
                    setColorStatus(tv_status, R.color.color_cancel)
                }
            }
        }

        private fun setColorStatus(tvStatus: TextView, color: Int) {
            tvStatus.setTextColor(ContextCompat.getColor(itemView.context, color))
        }
    }

}