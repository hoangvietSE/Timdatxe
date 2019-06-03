package com.example.anothertimdatxe.sprintlogin.updatepassword

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UpdatePasswordPresenter : BasePresenter {
    fun userUpdatePassword(email: String, token: String, password: String)
    fun driverUpdatePassword(email: String, token: String, password: String)
    fun userResendToken(email: String)
    fun driverResendToken(email: String)
}