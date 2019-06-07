package com.example.anothertimdatxe.sprintlogin.login

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface LoginPresenter : BasePresenter {
    fun login(email: String, password: String)
    fun loginUser(email: String, password: String)
    fun loginDriver(email: String, password: String)
    fun loginSocial(socialId: String, full_name: String, email: String, socialType: String)
}