package com.example.anothertimdatxe.sprinthome.revenue

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverRevenueResponse

interface RevenueDriverView : BaseView {
    fun showDetail(sum_trip: Int, sum_revenue: Int)
    fun showDataByMonth(data: List<DriverRevenueResponse>, isRefreshing: Boolean)
}