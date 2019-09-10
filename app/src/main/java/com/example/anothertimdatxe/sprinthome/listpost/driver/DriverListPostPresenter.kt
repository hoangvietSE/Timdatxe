package com.example.anothertimdatxe.sprinthome.listrequest.driver

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface DriverListPostPresenter : BasePresenter {
    fun setSpinnerStatus()
    fun fetListDriverPost()
    fun fetchListDriverPost(date: String)
    fun fetchListDriverPost(status: Int?)
    fun refreshList()
}