package com.example.anothertimdatxe.sprinthome.userpostcreated

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.UserListPostEntity

interface UserPostCreatedView : BaseView {
    fun getListUserPostCreated()
    fun initListUserPost()
    fun showListUserPostCreated(data: List<UserListPostEntity>)
    fun refreshListUserPostCreated()
}