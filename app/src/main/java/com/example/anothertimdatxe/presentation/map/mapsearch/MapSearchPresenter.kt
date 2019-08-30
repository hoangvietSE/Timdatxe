package com.example.anothertimdatxe.presentation.map.mapsearch

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface MapSearchPresenter : BasePresenter {
    fun setStartingPointLocation(location: String, placeId: String)
    fun setEndingPointLocation(location: String, placeId: String)
}