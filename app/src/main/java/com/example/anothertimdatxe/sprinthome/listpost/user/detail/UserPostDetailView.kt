package com.example.anothertimdatxe.sprinthome.listpost.user.detail

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.PostDetailResponse

interface UserPostDetailView : BaseView {
    fun showUserPostDetail(data: PostDetailResponse)
    fun onDeleteUserPostSuccess()
}