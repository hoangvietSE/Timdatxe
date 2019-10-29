package com.example.anothertimdatxe.presentation.book.user

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverPostDetailResponse

interface UserBookDetailView : BaseView {
    fun showUserBookDetail(data: DriverPostDetailResponse)
}