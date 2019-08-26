package com.example.anothertimdatxe.presentation.map.mapsearch

import com.example.anothertimdatxe.base.mvp.BaseView

interface MapSearchView : BaseView {
    fun showLocationStarting(location: String, placeId: String)
    fun showLocationEnding(location: String, placeId: String)
}