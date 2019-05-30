package com.example.anothertimdatxe.common

import android.content.Context
import android.graphics.Typeface.BOLD
import android.widget.TextView
import com.example.anothertimdatxe.R

class LoadingDialog(context: Context) : BaseCustomDialog(context) {
    var mProgress: TextView? = null
    override fun initView() {
        setCancelable(true)
        mProgress = findViewById(R.id.tvLoading)
    }

    override fun initData() {

    }

    override val layoutProgress: Int
        get() = R.layout.dialog_loading

}