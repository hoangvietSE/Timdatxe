package com.example.anothertimdatxe.sprinthome.profile.driver.driver_car

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverCarDetailResponse

interface DriverCarView : BaseView {
    fun showDriverCarDetail(response:DriverCarDetailResponse)
}