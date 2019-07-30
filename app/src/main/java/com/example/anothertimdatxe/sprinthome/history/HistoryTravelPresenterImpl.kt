package com.example.anothertimdatxe.sprinthome.history

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.BaseResponse
import com.example.anothertimdatxe.entity.response.DriverHistoryResponse
import com.example.anothertimdatxe.entity.response.UserHistoryResponse
import com.example.anothertimdatxe.util.CarBookingSharePreference

class HistoryTravelPresenterImpl(mView: HistoryTravelView) : BasePresenterImpl<HistoryTravelView>(mView), HistoryTravelPresenter {
    override fun getData(isHistoryUser: Boolean, isFreshing: Boolean) {
        if (isHistoryUser) {
            getUserHistory(isFreshing)
        } else {
            getDriverHistory(isFreshing)
        }
    }

    private fun getUserHistory(isFreshing: Boolean) {
        if (!isFreshing) mView!!.showLoading()
        val disposable = RetrofitManager.getUserHistory(object : ICallBack<BaseResponse<List<UserHistoryResponse>>> {
            override fun onSuccess(result: BaseResponse<List<UserHistoryResponse>>?) {
                mView!!.showUserHistory(result?.data!!,isFreshing)
                mView!!.setNumberTrip(result?.count_books!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
            }

        }, CarBookingSharePreference.getUserId())
    }

    private fun getDriverHistory(isFreshing: Boolean) {
        if (!isFreshing) mView!!.showLoading()
        val disposable = RetrofitManager.getDriverHistory(object : ICallBack<BaseResponse<List<DriverHistoryResponse>>> {
            override fun onSuccess(result: BaseResponse<List<DriverHistoryResponse>>?) {
                mView!!.showDriverHistory(result?.data!!,isFreshing)
                mView!!.setNumberTrip(result?.count_books!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
            }

        }, CarBookingSharePreference.getUserId())
    }

}