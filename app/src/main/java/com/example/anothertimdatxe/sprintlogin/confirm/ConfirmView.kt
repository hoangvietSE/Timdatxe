package com.example.anothertimdatxe.sprintlogin.confirm

import com.example.anothertimdatxe.base.mvp.BaseView

interface ConfirmView : BaseView {
    fun goToScreenHome()
    fun validateFromToken() : Boolean
    fun configSendSms()
    fun configResendSms()
}