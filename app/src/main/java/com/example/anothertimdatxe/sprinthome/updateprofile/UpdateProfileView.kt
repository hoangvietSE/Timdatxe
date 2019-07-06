package com.example.anothertimdatxe.sprinthome.updateprofile

import com.example.anothertimdatxe.base.mvp.BaseView

interface UpdateProfileView : BaseView {
    fun onFullNameError()
    fun onAddressError()
    fun onDescriptionError()
    fun onUpdateProfileError()
    fun backUserProfile()
}