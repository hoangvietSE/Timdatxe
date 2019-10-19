package com.example.anothertimdatxe.common

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.anothertimdatxe.extension.inflate

abstract class BaseCustomViewLinearLayout : LinearLayout {
    protected abstract val mLayoutRes: Int
    protected open val mStyleableRes: IntArray? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout()
        initView()
        initData()
        initListener()
        attrs?.let {
            initStyleable(it)
        }
    }

    protected open fun initView() {}

    protected open fun initData() {}

    protected open fun initListener() {}

    private fun initStyleable(attr: AttributeSet) {
        if (mStyleableRes != null) {
            val ta = context.obtainStyledAttributes(attr, mStyleableRes, 0, 0)
            initDataFromStyleable(ta)
            ta.recycle()
        }
    }

    abstract fun initDataFromStyleable(ta: TypedArray?)

    private fun setLayout() {
        context.inflate(mLayoutRes!!, this, true)
    }
}