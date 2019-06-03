package com.example.anothertimdatxe.sprintlogin.forgotpassword

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface ForgotPresenter : BasePresenter {
    fun onUserForgotPassword(email: String)
    fun onDriverForgotPassword(email: String)
}