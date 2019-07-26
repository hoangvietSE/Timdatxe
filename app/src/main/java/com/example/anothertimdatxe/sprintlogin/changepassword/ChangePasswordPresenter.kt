package com.example.anothertimdatxe.sprintlogin.changepassword

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface ChangePasswordPresenter : BasePresenter {
    fun userChangePassword(password: String, new_password: String)
}