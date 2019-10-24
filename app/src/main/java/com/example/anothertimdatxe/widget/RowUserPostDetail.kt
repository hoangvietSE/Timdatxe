package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible

class RowUserPostDetail(context: Context?, attrs: AttributeSet?) : BaseCustomViewRelativeLayout(context, attrs) {
    override val mLayoutId: Int
        get() = R.layout.layout_row_user_post_detail
    override val styleableId: IntArray?
        get() = R.styleable.BaseItemUserPostDetail
    private var mIcon: ImageView? = null
    private var mTitle: TextView? = null
    private var mDetail: TextView? = null
    private var lineView: View? = null

    override fun initView() {
        mIcon = findViewById(R.id.imv_icon)
        mTitle = findViewById(R.id.tv_title)
        mDetail = findViewById(R.id.tv_detail)
        lineView = findViewById(R.id.line_view)
    }

    override fun initDataStyleable(ta: TypedArray?) {
        val icon = ta?.getResourceId(R.styleable.BaseItemUserPostDetail_upd_icon, -1)
        val title = ta?.getString(R.styleable.BaseItemUserPostDetail_upd_title)
        val detail = ta?.getString(R.styleable.BaseItemUserPostDetail_upd_detail)
        val enableView = ta?.getBoolean(R.styleable.BaseItemUserPostDetail_upd_enable_line_view, true)
        setRowIcon(icon)
        setRowTitle(title)
        setRowDetail(detail)
        setEnableView(enableView)
    }

    fun setRowIcon(@DrawableRes icon: Int?) {
        icon?.let {
            if (it != -1) {
                mIcon?.setImageResource(it)
            }
        }
    }

    private fun setRowTitle(title: String?) {
        title?.let {
            mTitle?.text = title
        }
    }

    fun setRowDetail(detail: String?) {
        detail?.let {
            mDetail?.text = detail
        }
    }

    fun setDetailColor(color: Int?){
        color?.let {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                mDetail?.setTextColor(context.resources.getColor(it,null))
            }else{
                mDetail?.setTextColor(context.resources.getColor(it))
            }
        }
    }

    private fun setEnableView(enableView: Boolean?) {
        enableView?.let {
            if (it) {
                lineView?.visible()
            } else {
                lineView?.gone()
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}