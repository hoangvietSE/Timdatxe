package com.example.anothertimdatxe.common

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle

abstract class BaseCustomDialog(context: Context) : AlertDialog(context) {
    protected abstract val layoutProgress: Int
    abstract fun initView()
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutProgress)
        initView()
        initData()
    }
}