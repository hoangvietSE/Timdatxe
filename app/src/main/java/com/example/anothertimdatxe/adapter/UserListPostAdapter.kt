package com.example.anothertimdatxe.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter

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
        val viewHolder = holder as UserPostViewHolder
        viewHolder.formTitle.text = data?.title
        viewHolder.formStartingPoint.text = data?.app_start_province
        viewHolder.formEndingPoint.text = data?.app_end_province
        viewHolder.formSeat.text = data?.number_seat.toString()
        viewHolder.formStatus.text = data?.str_status
        viewHolder.formTime.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_3)
        viewHolder.formDate.text = DateUtil.formatDate(data?.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        when (data?.status) {
            Constant.USER_POST_PENDING -> {
                viewHolder.imvStatus.setImageResource(R.drawable.ic_status_pending)
                setColorStatus(viewHolder.formStatus, R.color.color_pending)
            }
            Constant.USER_POST_PUBLISHED -> {
                viewHolder.imvStatus.setImageResource(R.drawable.ic_status)
                setColorStatus(viewHolder.formStatus, R.color.color_pending)
            }
            Constant.USER_POST_DONE -> {
                viewHolder.imvStatus.setImageResource(R.drawable.ic_status_reject)
                setColorStatus(viewHolder.formStatus, R.color.color_reject)
            }
            Constant.USER_POST_FINISH -> {
                viewHolder.imvStatus.setImageResource(R.drawable.ic_status_finish)
                setColorStatus(viewHolder.formStatus, R.color.color_finish)
            }
            Constant.USER_POST_CANCEL -> {
                viewHolder.imvStatus.setImageResource(R.drawable.ic_status_cancel)
                setColorStatus(viewHolder.formStatus, R.color.color_cancel)
            }
        }
    }

    private fun setColorStatus(tvStatus: TextView, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvStatus.setTextColor(context!!.resources.getColor(color, null))
        } else {
            tvStatus.setTextColor(context!!.resources.getColor(color))
        }
    }

    class UserPostViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val formTitle: TextView = itemView.findViewById(R.id.formTitle)
        val formStartingPoint: TextView = itemView.findViewById(R.id.formStartingPoint)
        val formEndingPoint: TextView = itemView.findViewById(R.id.formEndingPoint)
        val formSeat: TextView = itemView.findViewById(R.id.tv_seat)
        val imvStatus: ImageView = itemView.findViewById(R.id.imv_status)
        val formStatus: TextView = itemView.findViewById(R.id.tv_status)
        val formTime: TextView = itemView.findViewById(R.id.formTime)
        val formDate: TextView = itemView.findViewById(R.id.formDate)
    }

}