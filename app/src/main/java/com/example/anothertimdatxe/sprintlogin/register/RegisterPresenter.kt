package com.example.anothertimdatxe.sprintlogin.register

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.RegisterRequest

interface RegisterPresenter : BasePresenter {
    fun registerDriver(request: RegisterRequest)
    fun registerUser(request: RegisterRequest)
}