package com.example.anothertimdatxe.presentation.map.mapparent

import android.content.Intent
import android.os.Build
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.map.TimDatXeBaseMap
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.presentation.map.mapsearch.MapSearchActivity
import com.example.anothertimdatxe.util.MapUtil
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_base_map.*

class MapParentActivity : TimDatXeBaseMap<MapParentPresenter>(), MapParentView {
    companion object {
        val TAG = MapParentActivity::class.java.simpleName
        const val REQUEST_CODE_LOCATION = 9001
    }

    private var mLocationStartingPoint: String? = null
    private var mLocationEndingPoint: String? = null

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
    }

    override fun initMap() {
        mFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mFragment?.getMapAsync(this)
        getLocationDevice()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        ToastUtil.show("Map on ready")
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
            it.text = resources.getString(R.string.timdatxe_basemap_title)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                when (resultCode) {
                    MapSearchActivity.RESULT_CODE -> {
                        mLocationStartingPoint = data?.extras?.getString(MapSearchActivity.STARTING_LOCATION_POINT)!!
                        mLocationEndingPoint = data?.extras?.getString(MapSearchActivity.ENDING_LOCATION_POINT)!!
                        setMarkerLocation(mLocationStartingPoint!!, mLocationEndingPoint!!)
                    }
                }
            }
        }
    }

    private fun setMarkerLocation(mLocationStartingPoint: String, mLocationEndingPoint: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addMarker(MapUtil.getLatLngFromAddress(this, mLocationStartingPoint), "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new, null)))
            addMarker(MapUtil.getLatLngFromAddress(this, mLocationEndingPoint), "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new, null)))
        } else {
            addMarker(MapUtil.getLatLngFromAddress(this, mLocationStartingPoint), "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new)))
            addMarker(MapUtil.getLatLngFromAddress(this, mLocationEndingPoint), "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new)))
        }
        mPresenter?.fetchWayPoints(mLocationStartingPoint, mLocationEndingPoint)
    }

    override fun routeSuccess(route: Route) {
        drawRouteSuccess(route)
    }

    override fun routeFail() {
        ToastUtil.show("Có lỗi xảy ra, vui lòng thử lại sau!")
    }
}