package com.example.anothertimdatxe.common

import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import com.example.anothertimdatxe.R

class LoadingDialog(context: Context) : BaseCustomDialog(context) {
    var mProgress: TextView? = null
    override fun initView() {
        setCancelable(true)
        val width = (context.resources.displayMetrics.widthPixels * 0.6).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        mProgress = findViewById(R.id.tvLoading)
    }

    override fun initData() {

    }

    override val layoutProgress: Int
        get() = R.layout.dialog_loading

}