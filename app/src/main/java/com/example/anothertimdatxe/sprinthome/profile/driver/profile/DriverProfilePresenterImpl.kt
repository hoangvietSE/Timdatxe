package com.example.anothertimdatxe.sprinthome.profile.driver.profile

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.DriverProfileResponse
import com.example.anothertimdatxe.util.NetworkUtil

class DriverProfilePresenterImpl(mView: DriverProfileView) : BasePresenterImpl<DriverProfileView>(mView), DriverProfilePresenter {
    private var totalPage: Int = 0
    private var pageIndex: Int = 1
    override fun getDriverInfo() {
        val disposable = RetrofitManager.getDriverInfo(object : ICallBack<BaseResult<DriverProfileResponse>> {
            override fun onSuccess(result: BaseResult<DriverProfileResponse>?) {
                val driverData = result?.data
                mView!!.showDriverInfo(driverData!!)
            }

            override fun onError(e: ApiException) {
            }

        })
    }

    override fun getUserReviewDriver() {
        val disposable = RetrofitManager.getUserReviewDriver(pageIndex)
                .doOnSubscribe {
                }
                .doFinally {
                    if (pageIndex <= totalPage) {
                        mView!!.enableLoadMore(true)
                    } else {
                        mView!!.enableLoadMore(false)
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView!!.showNoResult(it.data!!.size == 0)
                            mView!!.showListReview(it.data!!)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                )
    }
}