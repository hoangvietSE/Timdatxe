package com.example.anothertimdatxe.sprinthome.help

import com.example.anothertimdatxe.base.mvp.BaseView

interface SupportView : BaseView {
    fun onPhoneError()
    fun onEmailError()
    fun onAddressError()
    fun onEdittextError()
    fun showContactSuccess()
    fun showContactFail()
}