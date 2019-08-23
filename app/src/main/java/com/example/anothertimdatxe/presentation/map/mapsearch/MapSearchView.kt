package com.example.anothertimdatxe.presentation.map.mapsearch

import com.example.anothertimdatxe.base.mvp.BaseView

interface MapSearchView : BaseView {
    fun showLocationStarting(location: String)
    fun showLocationEnding(location: String)
}