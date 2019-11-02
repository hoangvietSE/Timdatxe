package com.example.anothertimdatxe.presentation.map.mapshow

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.google.android.gms.maps.model.LatLng

interface MapShowPresenter : BasePresenter {
    fun fetchWayPoints(origin: String, destination: String)
    fun fetchWayPoints(originLatLng: LatLng, destinationLatLng: LatLng)
}