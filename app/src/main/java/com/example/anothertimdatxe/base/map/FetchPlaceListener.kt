package com.example.anothertimdatxe.base.map

import com.google.android.gms.maps.model.LatLng

interface FetchPlaceListener {
    fun onSuccessFetchPlaces(mLatLng: LatLng)
    fun onErrorFetchPlaces()
}