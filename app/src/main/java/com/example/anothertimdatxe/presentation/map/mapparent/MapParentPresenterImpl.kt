package com.example.anothertimdatxe.presentation.map.mapparent

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.MapRetrofitManager
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.map.response.GoogleMapDirectionResponse
import com.google.android.gms.maps.model.LatLng

class MapParentPresenterImpl(mView: MapParentView) : BasePresenterImpl<MapParentView>(mView), MapParentPresenter {
    override fun fetchWayPoints(origin: String, destination: String) {
        val disposable = MapRetrofitManager.fetchWayPoints(object : ICallBack<GoogleMapDirectionResponse> {
            override fun onSuccess(result: GoogleMapDirectionResponse?) {
                result?.routes?.let {
                    if (it.size > 0) {
                        mView!!.routeSuccess(Route(
                                it.get(0)?.legs!![0]?.startLocation?.lat!!,
                                it.get(0)?.legs!![0]?.startLocation?.lng!!,
                                it.get(0)?.legs!![0]?.endLocation?.lat!!,
                                it.get(0)?.legs!![0]?.endLocation?.lng!!,
                                it.get(0)?.overviewPolyline?.points!!,
                                it.get(0)?.legs!![0]?.steps!!,
                                it.get(0)?.legs!![0]?.distance?.text!!,
                                it.get(0)?.legs!![0]?.duration?.value!!
                        ))
                        var listWayPoint: ArrayList<LatLng> = arrayListOf()
                        listWayPoint.add(LatLng(it.get(0)?.legs!![0]?.startLocation?.lat!!, it.get(0)?.legs!![0]?.startLocation?.lng!!))
                        it.get(0)?.legs!![0]?.steps?.let {
                            for (i in 0..it.size!! - 1) {
                                listWayPoint.add(LatLng(it.get(i)?.endLocation?.lat!!, it.get(i)?.endLocation?.lng!!))
                            }
                        }
                        mView!!.fetchWayPoint(listWayPoint)
                    } else {
                        mView!!.routeFail()
                    }
                } ?: mView!!.routeFail()
            }

            override fun onError(e: ApiException) {
                mView!!.routeFail()
            }

        }, "place_id:${origin}", "place_id:${destination}")
        addDispose(disposable)
    }
}