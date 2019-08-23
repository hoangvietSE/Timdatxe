package com.example.anothertimdatxe.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.libraries.places.api.model.Place
import java.util.*

object MapUtil {
    const val FIRST_LEFT_LATITUDE = -33.880490
    const val FIRST_RIGHT_LONGITUDE = 151.184363
    const val SECOND_LEFT_LATITUDE = -33.858754
    const val SECOND_RIGHT_LONGITUDE = 151.229596
    const val COUNTRY_CODE = "VN"
    const val ROLE_MAP_SEARCH_STARTING_POINT = "role starting point"
    const val ROLE_MAP_SEARCH_ENDING_POINT = "role ending point"
    val FIELD_NAME = Place.Field.NAME
    val FIELD_ADDRESS = Place.Field.ADDRESS
    val FIELD_LATLNG = Place.Field.LAT_LNG
    val FIELD_PHONE_NUMBER = Place.Field.PHONE_NUMBER
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
        } catch (e: Exception) {
            return ""
        }
    }

    fun getLocationState(context: Context, lat: Double, lng: Double): String {
        try {
            return getListAddress(context, lat, lng).get(0).adminArea
        } catch (e: Exception) {
            return ""
        }
    }

    fun getLocationCountry(context: Context, lat: Double, lng: Double): String {
        return getListAddress(context, lat, lng).get(0).countryName
    }

}