package com.example.anothertimdatxe.sprintlogin.login

import com.example.anothertimdatxe.mvp.BasePresenter

interface LoginPresenter : BasePresenter {
    fun login(email: String, password: String)
    fun loginUser(email: String, password: String)
    fun loginDriver(email: String, password: String)
}