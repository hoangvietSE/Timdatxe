package com.example.anothertimdatxe.sprinthome.postmore

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse

interface PostCreatedMoreView : BaseView {
    fun showLoadingProgress()
    fun hideLoadingProgress()
    fun showListPostFindUser(data: List<DriverPostResponse>, isRefreshing: Boolean)
    fun showListPostFindCar(data: List<UserPostResponse>, isRefreshing: Boolean)
    fun showListSearchFindUser(data: List<DriverPostResponse>)
    fun showListSearchFindCar(data: List<UserPostResponse>)
    fun showLoadingMoreProgress()
    fun hideLoadingMoreProgress()
    fun enableLoadingMore(boolean: Boolean)
}