package com.example.anothertimdatxe.base.map

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.util.DialogUtil
import com.example.anothertimdatxe.util.MapUtil
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.PolyUtil

abstract class TimDatXeBaseMap<T : BasePresenter> : BaseActivity<T>(), GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener, OnMapReadyCallback {
    companion object {
        val TAG: String = TimDatXeBaseMap::class.java.simpleName
        const val REQUEST_CODE_DIALOG_GOOGLE_PLAY_SERVICE = 9001
        const val REQUEST_CODE_PERMISSION = 1998
        const val REQUEST_CODE_SETTINGS_APP = 1999
        const val ZOOM_CAMERA = 13f
    }

    protected var mGoogleMap: GoogleMap? = null
    protected val permission: Array<String?> = arrayOfNulls(2)
    private var permissionNumber = 0
    protected var isCheckedPermissionSuccess = false
    protected var mFragment: SupportMapFragment? = null
    protected var mRoutePolyline: Polyline? = null

    //location device
    protected var isTheFirstTime: Boolean = true
    protected var mLastKnowLocation: Location? = null
    protected var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    protected var mPlaceFiled: List<Place.Field>? = null
    protected var request: FindCurrentPlaceRequest? = null
    protected var requestByPlaceId: FetchPlaceRequest? = null
    protected var mCurrentPlaceName: Array<String?>? = null
    protected var mCurrentPlaceAddress: Array<String?>? = null
    protected var mPlaceClient: PlacesClient? = null
    protected var mListener: DialogInterface.OnClickListener? = null
    protected var isGpsOnReady: Boolean = false
    protected var mGoogleApiClient: GoogleApiClient? = null

    override fun initView() {
        setUpToolbar()
        initClient()
        isGooglePlayServicesAvailable()
        setPermissionArray()
        initInterface()
        initData()
    }

    override fun onStart() {
        super.onStart()
        checkPermissionFromDevice()
    }

    private fun initClient() {
        if (!Places.isInitialized()) {
            Places.initialize(this, resources.getString(R.string.google_services_api_key))
        }
        mPlaceClient = Places.createClient(this)
        mPlaceFiled = listOf(
                MapUtil.FIELD_PLACE_ID,
                MapUtil.FIELD_LATLNG,
                MapUtil.FIELD_NAME,
                MapUtil.FIELD_ADDRESS,
                MapUtil.FIELD_LATLNG)
        mGoogleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).build()
        mGoogleApiClient?.connect()
    }

    protected open fun setUpToolbar() {
    }

    protected open fun initInterface() {
    }

    protected open fun initData() {
    }

    protected open fun gpsLocation() {
    }

    private fun isGooglePlayServicesAvailable() {
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google play Service is working")
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "Have error but can resolve")
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, REQUEST_CODE_DIALOG_GOOGLE_PLAY_SERVICE)
            dialog.show()
            return
        } else {
            Log.d(TAG, "Google Play Service doesn't support")
            return
        }
    }

    private fun checkPermissionFromDevice() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Checked Permission: GRANTED ACCESS_COARSE_LOCATION")
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Checked Permission: GRANTED ACCESS_FINE_LOCATION")
                isCheckedPermissionSuccess = true
                checkGpsInDevice()
            } else {
                Log.d(TAG, "Checked Permission: DENIED ACCESS_FINE_LOCATION")
                isCheckedPermissionSuccess = false
                ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_PERMISSION)
            }
        } else {
            Log.d(TAG, "Checked Permission: DENIED ACCESS_COARSE_LOCATION")
            isCheckedPermissionSuccess = false
            ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_PERMISSION)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                val isHasPermission = false
                if (grantResults.isNotEmpty()) {
                    grantResults.forEach {
                        if (it != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "Can't access permission device")
                            showDialogConfirmPermission()
                            return
                        }
                    }
                    isCheckedPermissionSuccess = true
                    checkGpsInDevice()
                }
            }
        }
    }

    override fun onMenuLeftCLick() {
        finish()
    }

    private fun setPermissionArray() {
        addPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun addPermission(permissionLocation: String) {
        permission[permissionNumber++] = permissionLocation
    }

    fun showDialogConfirmPermission() {
        DialogUtil.showAlertDialogNoTitle(
                this,
                resources.getString(R.string.timdatxe_basemap_dialog_content),
                false,
                resources.getString(R.string.timdatxe_basemap_dialog_postive),
                resources.getString(R.string.timdatxe_basemap_dialog_negative),
                object : DialogUtil.BaseAlertDialogListener {
                    override fun onPositiveClick(dialogInterface: DialogInterface) {
                        dialogInterface.dismiss()
                        goToSettingsApp()
                    }

                    override fun onNegativeClick(dialogInterface: DialogInterface) {
                        Log.d(TAG, "Map isn't ready because permission isn't allowed by user")
                        isCheckedPermissionSuccess = false
                        dialogInterface.dismiss()
                        finish()
                    }
                }
        )
    }

    private fun goToSettingsApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SETTINGS_APP -> {
                if (resultCode != Activity.RESULT_OK) {
                    isCheckedPermissionSuccess = false
                    finish()
                } else {
                    isCheckedPermissionSuccess = true
                    checkGpsInDevice()
                }
            }
            GpsUtil.GPS_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    initMap()
                } else {
                    ToastUtil.show("Vui lòng bật định vị trên thiết bị của bạn!")
                    finish()
                }
            }
        }
    }

    private fun checkGpsInDevice() {
        GpsUtil(this).turnGPSOn(object : GpsUtil.OnGpsListener {
            override fun startSettingGps() {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }

            override fun startResolution(rea: ResolvableApiException) {
                rea.startResolutionForResult(this@TimDatXeBaseMap, GpsUtil.GPS_REQUEST);
            }

            override fun gpsStatus(isGPSEnable: Boolean) {
                isGpsOnReady = isGPSEnable
                if (isGpsOnReady) {
                    initMap()
                }
            }
        }, mGoogleApiClient!!)
    }


    protected open fun initMap() {
    }

    protected fun getLocationDevice() {
        if (isCheckedPermissionSuccess) {
            try {
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                val location: Task<Location>? = mFusedLocationProviderClient?.lastLocation
                location?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mLastKnowLocation = task.result
                        LatLng(mLastKnowLocation?.latitude!!, mLastKnowLocation?.longitude!!).let {
                            moveCamera(
                                    it,
                                    ZOOM_CAMERA
                            )
                            if (isTheFirstTime) {
                                isTheFirstTime = false
                                addCircleShapeMarker(it, R.color.fillColor, R.color.strokeColor, 400.0)
                            }
                        }
                    } else {
                        Log.e(TAG, "Location Device Error!")
                    }
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "Location Device Error!", e)
            }
        }
    }

    override fun setListener() {

    }

    override fun onMapClick(p0: LatLng?) {

    }

    override fun onMapReady(p0: GoogleMap?) {

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    override fun onPolylineClick(p0: Polyline?) {

    }

    protected fun addMarker(position: LatLng, title: String, icon: BitmapDescriptor) {
        mGoogleMap?.addMarker(MarkerOptions()
                .position(position)
                .title(title)
                .icon(icon))
    }

    protected fun addMarker(position: LatLng, icon: BitmapDescriptor) {
        mGoogleMap?.addMarker(MarkerOptions()
                .position(position)
                .icon(icon))
    }

    protected fun addCircleShapeMarker(position: LatLng, fillColor: Int, strokeColor: Int, radius: Double) {
        val fillColorRes: Int?
        val strokeColorRes: Int?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fillColorRes = resources.getColor(fillColor, null)
            strokeColorRes = resources.getColor(strokeColor, null)
        } else {
            fillColorRes = resources.getColor(fillColor)
            strokeColorRes = resources.getColor(strokeColor)
        }
        mGoogleMap?.addCircle(CircleOptions()
                .center(position)
                .radius(radius)
                .strokeColor(strokeColorRes)
                .fillColor(0x220000FF)
                .strokeWidth(2f))
    }

    protected fun moveCamera(position: LatLng, zoom: Float) {
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom))
    }

    protected fun moveCameraBound(route: Route) {
        val originLatLng = LatLng(route.originLat, route.originLng)
        val destinationLatLng = LatLng(route.destinationLat, route.destinationLng)
        val builder = LatLngBounds.builder()
        builder.include(originLatLng)
        builder.include(destinationLatLng)
        val bounds = builder.build()
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.center, 50f))//ZOOM TO INSIDE WITH ZOOM_CAMERA IS 50f
        val mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80)//animate from inside to outside with padding screen is 80
        mGoogleMap?.animateCamera(mCameraUpdate)
    }

    protected fun moveCameraShow(mLatLngFrom: LatLng, mLatLngTo: LatLng) {
        val builder = LatLngBounds.Builder()
        builder.include(mLatLngFrom)
        builder.include(mLatLngTo)
        val bounds = builder.build()
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 80))
    }

    protected fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        var canvas = Canvas()
        var bitmap =
                Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight())
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    protected fun updateLocationUI(resource: Int) {
        addMapStyle(resource)
        if (mGoogleMap == null) return
        try {
            if (isCheckedPermissionSuccess) {
                mGoogleMap?.isMyLocationEnabled = true
                mGoogleMap?.isBuildingsEnabled = true
                mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = false
                mGoogleMap?.uiSettings?.isCompassEnabled = true
            } else {
                mGoogleMap?.isMyLocationEnabled = false
                mGoogleMap?.isBuildingsEnabled = false
                mGoogleMap?.uiSettings?.isCompassEnabled = false
                mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Can't update location UI", e)
        } catch (rn: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", rn)
        }

    }

    protected fun addMapStyle(resource: Int) {
        val success = mGoogleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, resource))
        if (success!!) {
            Log.d(TAG, "Style parsing success")
        } else {
            Log.d(TAG, "Style parsing fail")
        }
    }

    protected fun getCurrentPlace() {
        request = FindCurrentPlaceRequest.builder(mPlaceFiled!!).build()
        try {
            mPlaceClient?.findCurrentPlace(request!!)?.addOnSuccessListener { response ->
                mCurrentPlaceName = arrayOfNulls(response.placeLikelihoods.size)
                mCurrentPlaceAddress = arrayOfNulls(response.placeLikelihoods.size)
                var count = 0
                for (mPlaceLikeliHood in response.placeLikelihoods) {
                    Log.d(TAG, "Place ${mPlaceLikeliHood.place} has likelihood: ${mPlaceLikeliHood.likelihood}")
                    mCurrentPlaceName!![count] = mPlaceLikeliHood.place.name
                    mCurrentPlaceAddress!![count] = mPlaceLikeliHood.place.address
                }
                showDialogSetCurrentPlace()
            }?.addOnFailureListener { exception ->
                Log.e(TAG, "Get current place: error", exception)
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Get current place: error", e)
        }
    }

    protected fun fetchPlaceById(placeId: String, mListener: FetchPlaceListener) {
        requestByPlaceId = FetchPlaceRequest.builder(placeId, mPlaceFiled!!).build()
        mPlaceClient?.fetchPlace(requestByPlaceId!!)?.addOnSuccessListener { response ->
            val place = response.place
            Log.d(TAG, "Place found: ${place.name}")
            mListener.onSuccessFetchPlaces(place.latLng!!)
        }?.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: ${exception.message}", exception)
            }
            mListener.onErrorFetchPlaces()
        }
    }


    private fun showDialogSetCurrentPlace() {
        AlertDialog.Builder(this)
                .setTitle("Choose a current place")
                .setItems(mCurrentPlaceName, mListener)
                .show()
    }

    protected fun styleWithPolyline(): PolylineOptions {
        val mPolylineOptions = PolylineOptions()
        mPolylineOptions.geodesic(true)
        mPolylineOptions.width(9f)
        mPolylineOptions.startCap(RoundCap())
        mPolylineOptions.endCap(RoundCap())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPolylineOptions.color(resources.getColor(R.color.color_quantity, null))
        } else {
            mPolylineOptions.color(resources.getColor(R.color.color_quantity))
        }
        return mPolylineOptions
    }

    protected fun drawRouteSuccess(route: Route) {
        val mPolylineOptions = styleWithPolyline()
        route.steps.let {
            it.forEach {
                val points = PolyUtil.decode(it?.polyline?.points)//List<LatLng>
                for (point in points) {
                    mPolylineOptions.add(point)
                }
            }
        }
        mRoutePolyline = mGoogleMap?.addPolyline(mPolylineOptions)
    }

    protected fun clearWayPoint() {
        if (mRoutePolyline != null) {
            mRoutePolyline?.remove()
        }
    }

    protected fun clearMap() {
        if (mGoogleMap != null) {
            mGoogleMap?.clear()
        }
    }

}