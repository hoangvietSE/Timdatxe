package com.example.anothertimdatxe.sprinthome.postmore

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.PostCreatedFindCarRequest
import com.example.anothertimdatxe.request.PostCreatedFindUserRequest

interface PostCreatedMorePresenter : BasePresenter {
    fun getListFindUser(isRefreshing: Boolean)
    fun getListFindCar(isRefreshing: Boolean)
    fun getListSearchFindUser(request: PostCreatedFindUserRequest)
    fun getListSearchFindCar(request: PostCreatedFindCarRequest)
}