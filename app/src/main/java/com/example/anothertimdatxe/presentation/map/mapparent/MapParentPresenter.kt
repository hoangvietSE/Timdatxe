package com.example.anothertimdatxe.presentation.map.mapparent

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface MapParentPresenter : BasePresenter {
    fun fetchWayPoints(origin: String, destination: String)
}