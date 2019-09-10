package com.example.anothertimdatxe.sprinthome.listrequest.driver

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface DriverListRequestPresenter : BasePresenter {
    fun setSpinnerStatus()
    fun fetListDriverBook()
    fun fetchListDriverBook(date: String)
    fun fetchListDriverBook(status: Int?)
    fun refreshList()
}