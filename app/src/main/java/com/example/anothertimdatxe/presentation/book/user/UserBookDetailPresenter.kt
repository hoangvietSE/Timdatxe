package com.example.anothertimdatxe.presentation.book.user

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UserBookDetailPresenter : BasePresenter {
    fun fetchUserBookDetail(postId:Int)
}