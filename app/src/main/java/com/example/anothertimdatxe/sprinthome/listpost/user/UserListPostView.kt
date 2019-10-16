package com.example.anothertimdatxe.sprinthome.listpost.user

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.UserListPostEntity

interface UserListPostView : BaseView {
    fun setSpinnerStatus(listStatus: List<String>)
    fun enableLoadingMore(enable: Boolean)
    fun showLoadingMore()
    fun hideLoadingMore()
    fun showListUserPost(list: List<UserListPostEntity>)
    fun showRefreshing()
    fun hideRefreshing()
}