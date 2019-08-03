package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.DriverSearchRequest

interface DriverSearchPresenter : BasePresenter {
    fun fetchData()
    fun fetchDataLoadMore()
    fun fetchDataSearch(request: DriverSearchRequest)
}