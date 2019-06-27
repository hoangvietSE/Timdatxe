package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.layout_item_dialog_row.view.*

class BaseDialogRow : RelativeLayout {
    constructor(context: Context?) : super(context) {
        setLayout(context, this)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setLayout(context, this)
        attrs?.let {
            initStyleable(it)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout(context, this)
        attrs?.let {
            initStyleable(it)
        }
    }

    private fun setLayout(context: Context?, root: ViewGroup) {
        context!!.inflate(context, root, R.layout.layout_item_dialog_row)
    }

    private fun initStyleable(attrs: AttributeSet) {
        val ta: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BaseDialogRow, 0, 0)
        initDataFromStyleable(ta)
        ta.recycle()
    }

    private fun initDataFromStyleable(ta: TypedArray) {
        var mTitle = ta.getString(R.styleable.BaseDialogRow_row_title)
        setTitle(mTitle)
    }

    fun setTitle(mTitle: String?) {
        if (!mTitle.isNullOrEmpty()) {
            title.text = mTitle
        }
    }

    fun setContent(mDetail: String?) {
        if (!mDetail.isNullOrEmpty())
            detail.text = mDetail
    }

    fun setContent(mDetailId: Int?) {
        mDetailId?.let {
            detail.setText(it)
        }
    }
}