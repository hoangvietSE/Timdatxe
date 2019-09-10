package com.example.anothertimdatxe.presentation.drivercreatepost

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverCarResponse

interface DriverCreatePostView : BaseView {
    fun initSpinner(list: List<DriverCarResponse>)
    fun onErrorNoTitle()
    fun onErrorTitleHaveNumber()
    fun onErrorNoDate()
    fun onErrorNoTime()
    fun onErrorDateInPast()
    fun onErrorNoEstimate()
    fun onErrorInvalidEstimate()
    fun onErrorNoCarBrand()
    fun onErrorNoMoney()
    fun onErrorInvalidMoney()
    fun onErrorMinMoney()
    fun onSuccessCreatePost()
}