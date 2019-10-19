package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.BaseCustomViewLinearLayout

class RowUserRequestDetail(context: Context?, attrs: AttributeSet?) : BaseCustomViewLinearLayout(context, attrs) {
    override val mLayoutRes: Int
        get() = R.layout.item_user_request_detail
    override val mStyleableRes: IntArray?
        get() = R.styleable.BaseItemUserRequestDetail
    private var mIcon: ImageView? = null
    private var mTitle: TextView? = null
    private var mDetail: TextView? = null

    override fun initDataFromStyleable(ta: TypedArray?) {
        val icon = ta?.getResourceId(R.styleable.BaseItemUserRequestDetail_ubd_icon, 0)
        icon?.let {
            setIcon(it!!)
        }
        val title = ta?.getString(R.styleable.BaseItemUserRequestDetail_ubd_title)
        title?.let {
            setTitle(it)
        }
        val detail = ta?.getString(R.styleable.BaseItemUserRequestDetail_ubd_detail)
        detail?.let {
            setDetail(it)
        }
    }

    override fun initView() {
        mIcon = findViewById(R.id.imv_icon)
        mTitle = findViewById(R.id.tv_title)
        mDetail = findViewById(R.id.tv_detail)
    }

    private fun setIcon(@DrawableRes icon: Int) {
        mIcon?.setImageResource(icon)
    }

    private fun setTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            mTitle?.text = title
        }
    }

    fun setDetail(detail: String?) {
        if (!detail.isNullOrEmpty()) {
            mDetail?.text = detail
        }
    }


}