package com.example.anothertimdatxe.presentation.map.mapparent

import android.content.Intent
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.map.TimDatXeBaseMap
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_base_map.*

class MapParentActivity : TimDatXeBaseMap<MapParentPresenter>(), MapParentView {

    override fun getPresenter(): MapParentPresenter {
        return MapParentPresenterImpl(this)
    }

    override fun setListener() {
        edt_starting_point.setOnClickListener {
            startActivity(Intent())
        }
        edt_ending_point.setOnClickListener {
            startActivity(Intent())
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
}