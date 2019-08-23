package com.example.anothertimdatxe.presentation.map.mapsearch

import android.os.Handler
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl

class MapSearchPresenterImpl(mView: MapSearchView) : BasePresenterImpl<MapSearchView>(mView), MapSearchPresenter {
    override fun setStartingPointLocation(location: String) {
        mView!!.showLoading()
        Handler().postDelayed({
            mView!!.showLocationStarting(location)
        }, 400)
    }

    override fun setEndingPointLocation(location: String) {
        mView!!.showLoading()
        Handler().postDelayed({
            mView!!.showLocationEnding(location)
        }, 400)
    }
}