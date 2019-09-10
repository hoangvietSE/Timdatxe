package com.example.anothertimdatxe.base.map

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender
import android.location.LocationManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*


class GpsUtil(context: Context) {
    private var context: Context? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var locationManager: LocationManager? = null
    private var locationRequest: LocationRequest? = null

    companion object {
        val TAG = GpsUtil::class.java.simpleName
        const val GPS_REQUEST = 9001
    }

    init {
        this.context = context
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        mSettingsClient = LocationServices.getSettingsClient(context)

        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = (10 * 1000).toLong()
        locationRequest?.fastestInterval = (2 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest!!)
        mLocationSettingsRequest = builder.build()

        //**************************
        builder.setAlwaysShow(true) //this is the key ingredient
        //**************************
    }

    // method for turn on GPS
    fun turnGPSOn(onGpsListener: onGpsListener?, mGoogleApiClient: GoogleApiClient) {
        if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGpsListener?.gpsStatus(true)
        } else {
            val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest)
            result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
                override fun onResult(result: LocationSettingsResult) {
                    val status = result.status
                    val states = result.locationSettingsStates
                    when (status.statusCode) {
                        LocationSettingsStatusCodes.SUCCESS -> {
                            onGpsListener?.gpsStatus(true)
                        }
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                onGpsListener?.startResolution(status)
                            } catch (e: IntentSender.SendIntentException) {
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            onGpsListener?.startSettingGps()
                        }
                    }
                }

            })

        }
    }

    interface onGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
        fun startResolution(status: Status)
        fun startSettingGps()
    }

}