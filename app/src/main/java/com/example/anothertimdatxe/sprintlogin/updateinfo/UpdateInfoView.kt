package com.example.anothertimdatxe.sprintlogin.updateinfo

import com.example.anothertimdatxe.base.mvp.BaseView

interface UpdateInfoView : BaseView {
    fun onFullNameError()
    fun onDateError()
    fun onCheckBoxError()
    fun onUpdateSuccess()
}