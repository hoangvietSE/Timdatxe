package com.example.anothertimdatxe.sprinthome.profile.user

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.UserData

interface UserProfileView : BaseView {
    fun showData(userData: UserData)
}