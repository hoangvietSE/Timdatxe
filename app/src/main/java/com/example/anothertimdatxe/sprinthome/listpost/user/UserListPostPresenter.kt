package com.example.anothertimdatxe.sprinthome.listpost.user

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UserListPostPresenter : BasePresenter {
    fun initSpinnerStatus()
    fun fetchUserListPost()
    fun setStatus(status: Int)
    fun refreshData()
    fun setDate(date: String)
}