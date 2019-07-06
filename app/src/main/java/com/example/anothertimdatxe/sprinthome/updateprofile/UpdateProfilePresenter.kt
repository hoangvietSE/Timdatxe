package com.example.anothertimdatxe.sprinthome.updateprofile


import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.UserData

interface UpdateProfilePresenter : BasePresenter {
    fun setFilePart(file: String)
    fun updateUserProfile(data: UserData)
}