package com.example.anothertimdatxe.common

import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.mvp.BasePresenter

abstract class TimdatxeBaseActivity<T : BasePresenter> : BaseActivity<T>() {
    var mProressDialog: LoadingDialog? = null

    override fun hideLoading() {
        mProressDialog?.dismiss()
    }

    override fun showLoading() {
        mProressDialog = LoadingDialog(this)
        mProressDialog?.show()
    }
}