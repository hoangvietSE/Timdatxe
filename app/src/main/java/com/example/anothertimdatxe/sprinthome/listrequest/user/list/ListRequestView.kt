package com.example.anothertimdatxe.sprinthome.listrequest.user.list

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.ListUserBookResponse

interface ListRequestView : BaseView {
    fun setSpinnerStatus(listStatus: List<String>)
    fun enableLoadingMore(enable: Boolean)
    fun showLoadingMore()
    fun hideLoadingMore()
    fun showListUserPost(list: List<ListUserBookResponse>)
    fun showRefreshing()
    fun hideRefreshing()
}