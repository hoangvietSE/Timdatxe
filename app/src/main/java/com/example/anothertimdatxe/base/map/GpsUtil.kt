package com.example.anothertimdatxe.base.map

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*


class GpsUtil(context: Context) {
    private var context: Context? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var locationManager: LocationManager? = null
    private var locationRequest: LocationRequest? = null

    companion object {
        val TAG: String = GpsUtil::class.java.simpleName
        const val GPS_REQUEST = 9001
    }

    init {
        this.context = context
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        mSettingsClient = LocationServices.getSettingsClient(context)

        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 10000 / 2
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest!!)
        mLocationSettingsRequest = builder.build()

        //**************************
        builder.setAlwaysShow(true) //this is the key ingredient
        //**************************
    }

    // method for turn on GPS
    fun turnGPSOn(onGpsListener: OnGpsListener?, mGoogleApiClient: GoogleApiClient) {
        if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) onGpsListener?.let {
            it.gpsStatus(true)
        } else {
            mSettingsClient?.checkLocationSettings(mLocationSettingsRequest)
                    ?.addOnSuccessListener {
                        onGpsListener?.let {
                            it.gpsStatus(true)
                        }
                    }
                    ?.addOnFailureListener {
                        when ((it as ApiException).statusCode) {
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                                // Location settings are not satisfied. But could be fixed by showing the user
                                // a dialog.
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    onGpsListener?.startResolution(it as ResolvableApiException)
                                } catch (sie: IntentSender.SendIntentException) {
                                    Log.i(TAG, "PendingIntent unable to execute request.")
                                }
                            }
                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                // Location settings are not satisfied. However, we have no way to fix the
                                // settings so we won't show the dialog.
                                onGpsListener?.let {
                                    it.startSettingGps()
                                }
                            }
                        }
                    }
        }
    }

    interface OnGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
        fun startResolution(rea: ResolvableApiException)
        fun startSettingGps()
    }

}