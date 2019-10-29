package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout

abstract class BaseCustomViewRelativeLayout : RelativeLayout {
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

    private fun initStyleable(attrs: AttributeSet) {
        if (styleableId != null && styleableId!!.isNotEmpty()) {
            val a = context.theme.obtainStyledAttributes(attrs, styleableId, 0, 0)
            initDataStyleable(a)
            a.recycle()
        }
    }

    protected open fun initDataStyleable(a: TypedArray?) {
    }

    protected open val styleableId: IntArray? = null

    protected abstract val mLayoutId: Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

    internal fun setLayout() {
        var inflater = LayoutInflater.from(context)
        inflater.inflate(mLayoutId, this, true)
    }
}