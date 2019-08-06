package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse

interface DriverRequestDetailView : BaseView {
    fun finishScreen()
    fun showDetailUserPost(data: UserPostDetailResponse)
    fun cancelRequestSuccess(check: Boolean)
    fun cancelBookingSuccess(check: Boolean)
    fun finishTripSucceess(check: Boolean)
    fun finishBookSuccess(check: Boolean)
}