package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.item_revenue.view.*

class RowRevenue : LinearLayout {
    constructor(context: Context?) : super(context) {
        setLayout(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setLayout(context)
        attrs?.let {
            initStyleable(it)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout(context)
        attrs?.let {
            initStyleable(it)
        }
    }

    private fun setLayout(context: Context?) {
        context!!.inflate(context, this, R.layout.item_revenue)
    }

    private fun initStyleable(attrs: AttributeSet?) {
        val ta: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BaseRevenue, 0, 0)
        setDataFromTypedArray(ta)
        ta.recycle()
    }

    private fun setDataFromTypedArray(ta: TypedArray) {
        val title: String = ta.getString(R.styleable.BaseRevenue_mTitle)
        if (!title.isEmpty() || !title.isNullOrBlank()) {
            row_title.text = title
        }
    }

    fun setContent(data: String) {
        if (!data.isEmpty() || !data.isNullOrBlank()) {
            row_value.text = data
        }
    }

    fun setContent(data: Int) {
        row_value.setText(data)
    }
}