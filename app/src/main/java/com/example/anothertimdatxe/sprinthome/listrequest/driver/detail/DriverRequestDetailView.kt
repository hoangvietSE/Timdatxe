package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse

interface DriverRequestDetailView : BaseView {
    fun showDetailUserPost(data: UserPostDetailResponse)
}