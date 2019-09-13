package com.example.anothertimdatxe.presentation.map.mapparent

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.map.entity.Route
import com.google.android.gms.maps.model.LatLng

interface MapParentView : BaseView {
    fun routeSuccess(route: Route)
    fun routeFail()
    fun fetchWayPoint(list: ArrayList<LatLng>)
}