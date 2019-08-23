package com.example.anothertimdatxe.presentation.map.mapparent

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.MapRetrofitManager
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.map.response.GoogleMapDirectionResponse

class MapParentPresenterImpl(mView: MapParentView) : BasePresenterImpl<MapParentView>(mView), MapParentPresenter {
    override fun fetchWayPoints(origin: String, destination: String) {
        val disposable = MapRetrofitManager.fetchWayPoints(object : ICallBack<GoogleMapDirectionResponse> {
            override fun onSuccess(result: GoogleMapDirectionResponse?) {
                mView!!.routeSuccess(Route(
                        result?.routes?.get(0)?.legs!![0]?.startLocation?.lat!!,
                        result?.routes?.get(0)?.legs!![0]?.startLocation?.lng!!,
                        result?.routes?.get(0)?.legs!![0]?.endLocation?.lat!!,
                        result?.routes?.get(0)?.legs!![0]?.endLocation?.lng!!,
                        result?.routes?.get(0)?.overviewPolyline?.points!!,
                        result?.routes?.get(0)?.legs!![0]?.distance?.value!!,
                        result?.routes?.get(0)?.legs!![0]?.duration?.value!!
                ))
            }

            override fun onError(e: ApiException) {
                mView!!.routeFail()
            }

        }, origin, destination)
        addDispose(disposable)
    }
}