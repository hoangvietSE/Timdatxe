package com.example.anothertimdatxe.sprinthome.profile.driver.updateprofile

import com.example.anothertimdatxe.base.mvp.BaseView

interface DriverUpdateProfileView : BaseView {
    fun onFullNameError()
    fun onDateError()
    fun onAddressError()
    fun onDescriptionError()
    fun onUpdateSuccess()
}