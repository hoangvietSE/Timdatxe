package com.example.anothertimdatxe.base.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.SplashActivity
import com.example.anothertimdatxe.mvp.BasePresenter

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity() {

    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null//declare mPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        mPresenter = getPresenter()
        initView()
    }

    abstract public fun initView()

    abstract fun getPresenter(): T
}
