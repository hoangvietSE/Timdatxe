package com.example.anothertimdatxe.sprintlogin.forgotpassword

import com.example.anothertimdatxe.base.mvp.BaseView

interface ForgotView : BaseView {
    fun goToUpdate(token: String?)
    fun showDialogConfirmDirectForgot()

}