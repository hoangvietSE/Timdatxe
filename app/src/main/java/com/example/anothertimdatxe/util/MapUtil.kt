package com.example.anothertimdatxe.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

object MapUtil {
    fun getListAddress(context: Context, lat: Double, lng: Double): List<Address> {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(lat, lng, 1)
    }

    fun getLocationAddress(context: Context, lat: Double, lng: Double): String {
        return getListAddress(context, lat, lng).get(0).getAddressLine(0)
    }

    fun getLocationCity(context: Context, lat: Double, lng: Double): String {
        try {
            return getListAddress(context, lat, lng).get(0).locality
        }catch (e: Exception){
            return ""
        }
    }

    fun getLocationState(context: Context, lat: Double, lng: Double): String {
        try {
            return getListAddress(context, lat, lng).get(0).adminArea
        }catch (e: Exception){
            return ""
        }
    }

    fun getLocationCountry(context: Context, lat: Double, lng: Double): String {
        return getListAddress(context, lat, lng).get(0).countryName
    }

}