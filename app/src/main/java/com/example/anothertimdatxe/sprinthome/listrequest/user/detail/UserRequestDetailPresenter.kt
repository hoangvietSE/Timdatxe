package com.example.anothertimdatxe.sprinthome.listrequest.user.detail

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UserRequestDetailPresenter : BasePresenter {
    fun fetchUserRequestDetail(postId: Int)
    fun userCancelUserBook(userBookId: Int)
}