package com.example.anothertimdatxe.sprinthome.listpost.user.detail

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UserPostDetailPresenter : BasePresenter {
    fun fetchUserPostDetail(userPostId: Int)
    fun deleteUserPost(userPostId: Int)
}