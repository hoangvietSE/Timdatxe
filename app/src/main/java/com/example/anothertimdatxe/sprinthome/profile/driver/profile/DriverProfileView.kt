package com.example.anothertimdatxe.sprinthome.profile.driver.profile

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverProfileResponse
import com.example.anothertimdatxe.entity.response.UserReviewDriverResponse

interface DriverProfileView : BaseView {
    fun showDriverInfo(data: DriverProfileResponse)
    fun enableLoadMore(enable: Boolean)
    fun showLoadMoreProgress()
    fun hideLoadMoreProgress()
    fun showListReview(list: List<UserReviewDriverResponse>)
    fun showNoResult(check: Boolean)
}