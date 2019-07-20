package com.example.anothertimdatxe.sprinthome.revenue

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface RevenueDriverPresenter : BasePresenter {
    fun fetchData(month: Int, isRefreshing: Boolean)
}