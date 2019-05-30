package com.example.anothertimdatxe.sprintlogin.register

import com.example.anothertimdatxe.base.mvp.BaseView

interface RegisterView : BaseView {
    fun goToConfirm(token_register: String, checkRegister: Int)
}