package com.example.anothertimdatxe.presentation.map.mapshow

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.map.entity.Route

interface MapShowView : BaseView {
    fun routeSuccess(route: Route)
    fun routeFail()
}