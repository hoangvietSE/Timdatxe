package com.example.anothertimdatxe.common

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.mvp.BasePresenter

abstract class TimdatxeBaseActivity<T : BasePresenter> : BaseActivity<T>() {

    override fun hideLoading() {

    }

    override fun showLoading() {

    }
}