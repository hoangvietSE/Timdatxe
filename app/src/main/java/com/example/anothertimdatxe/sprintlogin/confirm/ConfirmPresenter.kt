package com.example.anothertimdatxe.sprintlogin.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.ActiveRequest

interface ConfirmPresenter : BasePresenter {
    fun onActiveDriver(request: ActiveRequest)
    fun onActiveUser(request: ActiveRequest)
}