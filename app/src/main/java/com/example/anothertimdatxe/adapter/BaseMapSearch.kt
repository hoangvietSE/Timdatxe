package com.example.anothertimdatxe.adapter

import android.content.Context
import android.util.Log
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.util.MapUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class BaseMapSearch(context: Context, var mListener: MapSearchListener) {
    val TAG = BaseMapSearch::class.java.simpleName
    private var mPlacesClient: PlacesClient? = null
    private var token: AutocompleteSessionToken? = null
    private var bounds: RectangularBounds? = null
    private var request: FindAutocompletePredictionsRequest? = null

    init {
        if (!Places.isInitialized()) {
            Places.initialize(context, context.resources.getString(R.string.google_services_api_key))
        }
        mPlacesClient = Places.createClient(context)
        // Create a new Places client instance.
        //The session begins when the user starts typing a query and concludes when they select a place
        token = AutocompleteSessionToken.newInstance()
        //specifies latitude and longitude bounds to constrain results to the specified region
        bounds = RectangularBounds.newInstance(
                LatLng(MapUtil.FIRST_LEFT_LATITUDE, MapUtil.FIRST_RIGHT_LONGITUDE),
                LatLng(MapUtil.SECOND_LEFT_LATITUDE, MapUtil.SECOND_RIGHT_LONGITUDE))
    }

    fun findLocationPredictionAutoComplte(queryText: String, role: String) {
        if (queryText.length == 0) {
            mListener.onMapSearchSuccess(listOf(), role)
            return
        }
        // Use the builder to create a FindAutocompletePredictionsRequest
        request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                .setSessionToken(token)
                .setCountry(MapUtil.COUNTRY_CODE)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setQuery(queryText)
                .build()
        mPlacesClient?.findAutocompletePredictions(request!!)?.addOnSuccessListener { response ->
            for (i in 0..response.autocompletePredictions.size - 1) {
                Log.d(TAG, response.autocompletePredictions[i].getPrimaryText(null).toString())
                Log.d(TAG, response.autocompletePredictions[i].getSecondaryText(null).toString())
            }
            mListener.onMapSearchSuccess(response.autocompletePredictions, role)
        }?.addOnFailureListener {
            if (it is ApiException) {
                Log.e(TAG, it.statusCode.toString(), it)
            }
            mListener.onMapSearchFail(it)
        }
    }
}

interface MapSearchListener {
    fun onMapSearchSuccess(list: List<AutocompletePrediction>, role: String)
    fun onMapSearchFail(exception: Exception)
}