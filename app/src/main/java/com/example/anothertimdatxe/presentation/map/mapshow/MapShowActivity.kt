package com.example.anothertimdatxe.presentation.map.mapshow

import android.os.Build
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.map.TimDatXeBaseMap
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map_show.*

class MapShowActivity : TimDatXeBaseMap<MapShowPresenter>(), MapShowView {
    companion object {
        const val ORIGIN_LOCATION = "origin_location"
        const val DESTINATION_LOCATION = "destination_location"
        const val LAT_FROM = "lat_from"
        const val LNG_FROM = "lng_from"
        const val LAT_TO = "lat_to"
        const val LNG_TO = "lng_to"
    }

    private var latFrom: Double? = null
    private var lngFrom: Double? = null
    private var latTo: Double? = null
    private var lngTo: Double? = null
    private var mOrigin: String? = null
    private var mDestination: String? = null

    override val layoutRes: Int
        get() = R.layout.activity_map_show

    override fun getPresenter(): MapShowPresenter {
        return MapShowPresenterImpl(this)
    }

    override fun initMap() {
        mFragment = supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
        mFragment?.getMapAsync(this)
    }

    override fun setListener() {
        btn_confirm.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap
        mGoogleMap?.setOnMarkerClickListener(this)
        mGoogleMap?.setOnMapClickListener(this)
        mGoogleMap?.setOnPolylineClickListener(this)
        updateLocationUI(R.raw.map_style)
    }

    override fun initData() {
        latFrom = intent.getDoubleExtra(LAT_FROM, 0.0)
        lngFrom = intent.getDoubleExtra(LNG_FROM, 0.0)
        latTo = intent.getDoubleExtra(LAT_TO, 0.0)
        lngTo = intent.getDoubleExtra(LNG_TO, 0.0)
        mOrigin = intent.getStringExtra(ORIGIN_LOCATION)
        mDestination = intent.getStringExtra(DESTINATION_LOCATION)
        mPresenter?.fetchWayPoints(mOrigin!!, mDestination!!, LatLng(latFrom!!, lngFrom!!), LatLng(latTo!!, lngTo!!))
    }

    override fun routeSuccess(route: Route) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addMarker(LatLng(latFrom!!, lngFrom!!), "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new, null)))
            addMarker(LatLng(latTo!!, lngTo!!), "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new, null)))
        } else {
            addMarker(LatLng(latFrom!!, lngFrom!!), "Điểm xuất phát", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_start_point_new)))
            addMarker(LatLng(latTo!!, lngTo!!), "Điểm kết thúc", getMarkerIconFromDrawable(resources.getDrawable(R.drawable.ic_end_point_new)))
        }
        drawRouteSuccess(route)
        moveCameraShow(LatLng(latFrom!!, lngFrom!!), LatLng(latTo!!, lngTo!!))
    }

    override fun routeFail() {
        ToastUtil.show("Có lỗi xảy ra, vui lòng thử lại sau!")
    }
}