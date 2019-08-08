package com.example.anothertimdatxe.sprinthome.profile.driver.driver_car

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.util.NetworkUtil

class DriverCarPresenterImpl(mView: DriverCarView) : BasePresenterImpl<DriverCarView>(mView), DriverCarPresenter {
    override fun getDriverCarInfo(id: Int) {
        addDispose(RetrofitManager.driverCarDetail(id)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.showDriverCarDetail(it.data!!)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                ))
    }
}