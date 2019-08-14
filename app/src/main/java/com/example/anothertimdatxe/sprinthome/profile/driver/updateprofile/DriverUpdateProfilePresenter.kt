package com.example.anothertimdatxe.sprinthome.profile.driver.updateprofile

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.response.DriverProfileResponse

interface DriverUpdateProfilePresenter : BasePresenter {
    fun setFilePath(file: String, requestCode: Int)
    fun updateProfile(data: DriverProfileResponse)
}