package com.example.anothertimdatxe.common

import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.mvp.BasePresenter

abstract class TimdatxeBaseFragment<T : BasePresenter> : BaseFragment<T>() {
    private var mProressDialog: LoadingDialog? = null

    override fun showLoading() {
        super.showLoading()
        if (mProressDialog == null) {
            mProressDialog = LoadingDialog(context!!)
        }
        mProressDialog!!.show()
    }

    override fun hideLoading() {
        super.hideLoading()
        mProressDialog!!.dismiss()
    }
}