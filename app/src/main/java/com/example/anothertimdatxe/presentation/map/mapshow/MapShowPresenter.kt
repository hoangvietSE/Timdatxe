package com.example.anothertimdatxe.presentation.map.mapshow

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface MapShowPresenter : BasePresenter {
    fun fetchWayPoints(origin: String, destination: String)
}