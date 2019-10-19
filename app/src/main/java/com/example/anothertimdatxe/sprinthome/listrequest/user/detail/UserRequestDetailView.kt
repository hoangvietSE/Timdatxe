package com.example.anothertimdatxe.sprinthome.listrequest.user.detail

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.UserRequestDetailResponse

interface UserRequestDetailView : BaseView {
    fun showUserRequestDetail(data: UserRequestDetailResponse)
    fun userCancelUserRequestSuccess(msg: String)
    fun userCancelUserRequestError()
}