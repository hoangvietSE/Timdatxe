package com.example.anothertimdatxe.sprinthome.listrequest.driver

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverListPostResponse

interface DriverListPostView : BaseView {
    fun setSpinnerStatus(list: List<String>)
    fun hideLoadingItem()
    fun showLoadingItem()
    fun enableLoadingMore(enable: Boolean)
    fun setListItem(list: List<DriverListPostResponse>)
    fun showPreview()
    fun hidePreview()
    fun showNoResult(check: Boolean)
}