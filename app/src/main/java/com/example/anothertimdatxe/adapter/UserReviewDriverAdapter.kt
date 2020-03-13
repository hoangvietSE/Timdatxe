package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.UserReviewDriverResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.DateUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_review_driver.*

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
        val data = getItem(position, UserReviewDriverResponse::class.java)
        holder.bind(data!!)
    }

    class UserReviewViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun bind(data: Any) {
            data as UserReviewDriverResponse
            tv_customer.text = data.fullName
            rtb_review.rating = data.vote!!.toFloat()
            tv_rating_date.text = DateUtil.formatDate(data.createdAt!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23)
            imv_avatar.setAvatar(itemView.context, data.avatar)
            tv_review.text = data.content
        }
    }
}