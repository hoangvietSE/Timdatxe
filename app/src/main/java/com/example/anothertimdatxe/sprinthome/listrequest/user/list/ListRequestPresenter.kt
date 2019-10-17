package com.example.anothertimdatxe.sprinthome.listrequest.user.list

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface ListRequestPresenter : BasePresenter {
    fun initSpinnerStatus()
    fun fetchUserListBook()
    fun setStatus(status: Int)
    fun refreshData()
    fun setDate(date: String)
}