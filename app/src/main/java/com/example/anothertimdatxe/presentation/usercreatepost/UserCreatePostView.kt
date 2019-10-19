package com.example.anothertimdatxe.presentation.usercreatepost

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.request.UserCreatePostRequest

interface UserCreatePostView : BaseView {
    fun onTitleEmpty()
    fun onTitleError()
    fun onDateEmpty()
    fun onDateError()
    fun onTimeEmpty()
    fun onSeatEmpty()
    fun onSeatError()
    fun showWayPoints(data: UserCreatePostRequest, wayPoints: String)
    fun onCreatePostError()
    fun onCreatePostSuccess()
}