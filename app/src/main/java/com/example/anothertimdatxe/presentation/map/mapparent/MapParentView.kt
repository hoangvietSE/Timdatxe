package com.example.anothertimdatxe.presentation.map.mapparent

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.map.entity.Route

interface MapParentView : BaseView {
    fun routeSuccess(route: Route)
    fun routeFail()
}