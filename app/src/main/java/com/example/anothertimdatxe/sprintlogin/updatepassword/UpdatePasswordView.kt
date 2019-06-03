package com.example.anothertimdatxe.sprintlogin.updatepassword

import com.example.anothertimdatxe.base.mvp.BaseView

interface UpdatePasswordView : BaseView {
    fun showCompleteDiaglog()
    fun showAlertDialogSendTokenSuccess()
    fun showAlertDialogSendTokenToPhone()
    fun sendTokenToUserPhone()
    fun changePasswordWithUser()
    fun changePasswordWithDriver()
    fun runTimeSendToken(totalTime: Long)
    fun goToLoginHome()
}