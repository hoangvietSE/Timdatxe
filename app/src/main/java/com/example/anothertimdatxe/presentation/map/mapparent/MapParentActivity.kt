package com.example.anothertimdatxe.presentation.map.mapparent

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.map.FetchPlaceListener
import com.example.anothertimdatxe.base.map.TimDatXeBaseMap
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.presentation.drivercreatepost.DriverCreatePostActivity
import com.example.anothertimdatxe.presentation.map.mapsearch.MapSearchActivity
import com.example.anothertimdatxe.presentation.usercreatepost.UserCreatePostActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.soict.hoangviet.baseproject.extension.toast
import kotlinx.android.synthetic.main.activity_base_map.*

class MapParentActivity : TimDatXeBaseMap<MapParentPresenter>(), MapParentView {
    companion object {
        val TAG: String = MapParentActivity::class.java.simpleName
        const val REQUEST_CODE_LOCATION = 9001
        const val REQUEST_CODE_CREATE_POST = 9002
    }

    private var mLocationStartingPointId: String? = null
    private var mLocationEndingPointId: String? = null
    private var mLocationStartingPoint: String? = null
    private var mLocationEndingPoint: String? = null
    private var mDistance: String? = null
    private var mDuration: Int? = null
    private var mList: ArrayList<LatLng> = arrayListOf()

    override val layoutRes: Int
        get() = R.layout.activity_base_map

    override fun getPresenter(): MapParentPresenter {
        return MapParentPresenterImpl(this)
    }

    override fun setListener() {
        edt_starting_point.setOnClickListener {
            startActivityForResult(Intent(this, MapSearchActivity::class.java), REQUEST_CODE_LOCATION)
        }
        edt_ending_point.setOnClickListener {
            startActivityForResult(Intent(this, MapSearchActivity::class.java), REQUEST_CODE_LOCATION)
        }
        btn_gps.setOnClickListener {
            gpsLocation()
        }
        btn_confirm.setOnClickListener {
            if (CarBookingSharePreference.getUserData()?.isUser!!) {
                startActivityForResult(Intent(this, UserCreatePostActivity::class.java).apply {
                    putExtra(UserCreatePostActivity.EXTRA_STARTING_POINT, mLocationStartingPoint)
                    putExtra(UserCreatePostActivity.EXTRA_ENDING_POINT, mLocationEndingPoint)
                    putExtra(UserCreatePostActivity.EXTRA_DISTANCE, mDistance)
                    putExtra(UserCreatePostActivity.EXTRA_DURATION, mDuration)
                    putParcelableArrayListExtra(UserCreatePostActivity.EXTRA_LIST_WAYPOINT, mList)
                    putExtra(UserCreatePostActivity.EXTRA_IS_CREATE_POST, true)
                }, REQUEST_CODE_CREATE_POST)
            } else {
                startActivityForResult(Intent(this, DriverCreatePostActivity::class.java).apply {
                    putExtra(DriverCreatePostActivity.EXTRA_STARTING_POINT, mLocationStartingPoint)
                    putExtra(DriverCreatePostActivity.EXTRA_ENDING_POINT, mLocationEndingPoint)
                    putExtra(DriverCreatePostActivity.EXTRA_DISTANCE, mDistance)
                    putExtra(DriverCreatePostActivity.EXTRA_DURATION, mDuration)
                    putParcelableArrayListExtra(DriverCreatePostActivity.EXTRA_LIST_WAYPOINT, mList)
                    putExtra(DriverCreatePostActivity.EXTRA_IS_CREATE_POST, true)
                }, REQUEST_CODE_CREATE_POST)
            }
        }
    }

    override fun initMap() {
        mFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mFragment?.getMapAsync(this)
        getLocationDevice()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (isTheFirstTime) toast("Map on ready")
        mGoogleMap = googleMap
        mGoogleMap?.setOnMapClickListener(this)
        mGoogleMap?.setOnPolylineClickListener(this)
        mGoogleMap?.setOnMarkerClickListener(this)
        updateLocationUI(R.raw.map_style)
    }

    override fun gpsLocation() {
        moveCamera(LatLng(mLastKnowLocation?.latitude!!, mLastKnowLocation?.longitude!!), ZOOM_CAMERA)
    }

    override fun setUpToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.timdatxe_basemap_title).toUpperCase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                when (resultCode) {
                    MapSearchActivity.RESULT_CODE -> {
                        mLocationStartingPointId = data?.extras?.getString(MapSearchActivity.STARTING_LOCATION_POINT_ID)
                        mLocationEndingPointId = data?.extras?.getString(MapSearchActivity.ENDING_LOCATION_POINT_ID)
                        mLocationStartingPoint = data?.extras?.getString(MapSearchActivity.STARTING_LOCATION_POINT)
                        mLocationEndingPoint = data?.extras?.getString(MapSearchActivity.ENDING_LOCATION_POINT)
                        edt_starting_point.setText(mLocationStartingPoint)
                        edt_ending_point.setText(mLocationEndingPoint)
                        csl_confirm.visible()
                        setMarkerLocation(mLocationStartingPointId!!, mLocationEndingPointId!!)
                    }
                }
            }
            REQUEST_CODE_CREATE_POST -> {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }
    }

    private fun setMarkerLocation(mLocationStartingPointId: String, mLocationEndingPointId: String) {
        mPresenter?.fetchWayPoints(mLocationStartingPointId, mLocationEndingPointId)
    }

    val mListenerFetchPlaceStartingById = object : FetchPlaceListener {
        override fun onSuccessFetchPlaces(mLatLng: LatLng) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addMarker(mLatLng, "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new, null)))
            } else {
                addMarker(mLatLng, "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new)))
            }
        }

        override fun onErrorFetchPlaces() {
        }

    }

    val mListenerFetchPlaceEndingById = object : FetchPlaceListener {
        override fun onSuccessFetchPlaces(mLatLng: LatLng) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addMarker(mLatLng, "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new, null)))
            } else {
                addMarker(mLatLng, "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new)))
            }
        }

        override fun onErrorFetchPlaces() {
        }
    }

    override fun routeSuccess(route: Route) {
        addMarkerMap(LatLng(route.originLat,route.originLng),LatLng(route.destinationLat,route.destinationLng))
        drawRouteSuccess(route)
        moveCameraBound(route)
        mDistance = route.distance
        mDuration = route.time
    }

    private fun addMarkerMap(originLatLng: LatLng, destinationLatLng: LatLng) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addMarker(originLatLng, "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new, null)))
            addMarker(destinationLatLng, "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new, null)))
        } else {
            addMarker(originLatLng, "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new)))
            addMarker(destinationLatLng, "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new, null)))
        }
    }

    override fun routeFail() {
        toast("Có lỗi xảy ra, vui lòng thử lại sau!")
    }

    override fun fetchWayPoint(list: ArrayList<LatLng>) {
        mList.addAll(list)
    }

}