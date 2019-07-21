package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity

class DriverSearchActivity : BaseActivity<DriverSearchPresenter>(), DriverSearchView {
    override val layoutRes: Int
        get() = R.layout.activity_driver_search

    override fun getPresenter(): DriverSearchPresenter {
        return DriverSearchPresenterImpl(this)
    }

    override fun initView() {

    }
}