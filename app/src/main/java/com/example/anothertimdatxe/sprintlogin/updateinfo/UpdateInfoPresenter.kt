package com.example.anothertimdatxe.sprintlogin.updateinfo

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.UserData

interface UpdateInfoPresenter : BasePresenter {
    fun updateInfo(userData: UserData)
}