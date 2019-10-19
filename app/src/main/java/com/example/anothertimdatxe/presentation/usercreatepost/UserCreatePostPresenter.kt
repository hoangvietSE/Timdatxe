package com.example.anothertimdatxe.presentation.usercreatepost

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.UserCreatePostRequest
import com.google.android.gms.maps.model.LatLng

interface UserCreatePostPresenter : BasePresenter {
    fun createPost(data: UserCreatePostRequest)
    fun getWayPoints(data: UserCreatePostRequest, listWayPoints: ArrayList<LatLng>)
}