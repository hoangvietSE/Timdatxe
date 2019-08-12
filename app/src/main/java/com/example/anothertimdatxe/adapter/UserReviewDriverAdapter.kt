package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.UserReviewDriverResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_review_driver.view.*

class UserReviewDriverAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(context!!.inflate(R.layout.loading, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return UserReviewViewHolder(context!!.inflate(R.layout.item_review_driver, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val userReviewViewHolder = holder as UserReviewViewHolder
        val data = getItem(position, UserReviewDriverResponse::class.java)
        userReviewViewHolder.tvCustomer.text = data!!.fullName
        userReviewViewHolder.rtbReview.rating = data!!.vote!!.toFloat()
        userReviewViewHolder.tvRatingDate.text = DateUtil.formatDate(data!!.createdAt!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
        userReviewViewHolder.imvAvatar.setAvatar(context!!, data!!.avatar)
        userReviewViewHolder.tvReview.text = data!!.content
    }

    class UserReviewViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val tvCustomer: TextView = itemView.tv_customer
        val rtbReview: me.zhanghai.android.materialratingbar.MaterialRatingBar = itemView.rtb_review
        val tvRatingDate: TextView = itemView.tv_rating_date
        val tvReview: TextView = itemView.tv_review
        val imvAvatar: ImageView = itemView.imv_avatar
    }
}