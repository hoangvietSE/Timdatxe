package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.layout_title_row.view.*

class BaseTitleRow : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout(context)
        attrs?.let {
            initStyleable(it)
        }
    }

    private fun setLayout(context: Context?) {
        context!!.inflate(R.layout.layout_title_row, this, true)
    }

    private fun initStyleable(attrs: AttributeSet) {
        val typeArray = context!!.obtainStyledAttributes(attrs, R.styleable.BaseTitleRow, 0, 0)
        initDateFormStyleable(typeArray)
        typeArray.recycle()
    }

    private fun initDateFormStyleable(typeArray: TypedArray) {
        val title = typeArray.getString(R.styleable.BaseTitleRow_create_post_row_title)
        val enable = typeArray.getBoolean(R.styleable.BaseTitleRow_create_post_enable_importance, true)
        val drawable = typeArray.getDrawable(R.styleable.BaseTitleRow_create_post_row_icon)
        setTitle(title)
        setEnable(enable)
        setIcon(drawable)
    }

    private fun setTitle(title: String) {
        if (!title.isNullOrEmpty()!!) {
            tv_title.text = title
        }
    }

    private fun setEnable(enable: Boolean) {
        if (enable) {
            tv_importance.visibility = View.VISIBLE
        } else {
            tv_importance.visibility = View.GONE
        }
    }

    private fun setIcon(drawable: Drawable) {
        imv_title.setImageDrawable(drawable)
    }
}