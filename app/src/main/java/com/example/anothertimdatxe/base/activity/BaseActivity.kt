package com.example.anothertimdatxe.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BaseView

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BaseView {

    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        mPresenter = getPresenter()
        //initView
        mPresenter.let {
            it!!.onStart()
        }
    }

    abstract fun getPresenter(): T
}