package com.example.anothertimdatxe.sprinthome.profile.driver.driver_car

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface DriverCarPresenter : BasePresenter {
    fun getDriverCarInfo(id: Int)
}