package com.example.anothertimdatxe.presentation.drivercreatepost

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.DriverCreatePostRequest

interface DriverCreatePostPresenter : BasePresenter {
    fun fetchDriverCarInfo()
    fun driverCreatePost(request: DriverCreatePostRequest)
}