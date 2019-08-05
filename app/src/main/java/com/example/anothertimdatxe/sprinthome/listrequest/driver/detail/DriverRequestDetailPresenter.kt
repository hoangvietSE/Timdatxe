package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface DriverRequestDetailPresenter : BasePresenter {
    fun getDataUserPost(id: Int)
    fun cancelRequest(driverBookOptionId: Int)
    fun cancelBooking(driverBookId: Int)
    fun finishTripDriverBook(userPostId: Int)
}