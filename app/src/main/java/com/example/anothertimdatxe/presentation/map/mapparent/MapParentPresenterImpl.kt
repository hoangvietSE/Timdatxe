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
                result?.routes?.let {
                    if (it.size > 0) {
                        mView!!.routeSuccess(Route(
                                it.get(0)?.legs!![0]?.startLocation?.lat!!,
                                it.get(0)?.legs!![0]?.startLocation?.lng!!,
                                it.get(0)?.legs!![0]?.endLocation?.lat!!,
                                it.get(0)?.legs!![0]?.endLocation?.lng!!,
                                it.get(0)?.overviewPolyline?.points!!,
                                it.get(0)?.legs!![0]?.distance?.value!!,
                                it.get(0)?.legs!![0]?.duration?.value!!
                        ))
                    }else{
                        mView!!.routeFail()
                    }
                } ?: mView!!.routeFail()
            }

            override fun onError(e: ApiException) {
                mView!!.routeFail()
            }

        }, origin, destination)
        addDispose(disposable)
    }
}