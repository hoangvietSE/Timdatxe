package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverSearchResponse

interface DriverSearchView : BaseView {
    fun enableLoadingMore(enable: Boolean)
    fun showNoResult(check: Boolean)
    fun showListDriverSearch(data: List<DriverSearchResponse>)
    fun showListDriverSearchOnClick(data: List<DriverSearchResponse>)
    fun showLoadingMore()
    fun hideLoadingMore()
}