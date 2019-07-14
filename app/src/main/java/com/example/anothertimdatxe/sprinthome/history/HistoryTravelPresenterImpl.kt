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
    override fun getData(isHistoryUser: Boolean) {
        if (isHistoryUser) {
            getUserHistory()
        } else {
            getDriverHistory()
        }
    }

    private fun getUserHistory() {
        mView!!.showLoading()
        val disposable = RetrofitManager.getUserHistory(object : ICallBack<BaseResponse<List<UserHistoryResponse>>> {
            override fun onSuccess(result: BaseResponse<List<UserHistoryResponse>>?) {
                mView!!.showUserHistory(result?.data!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
            }

        }, CarBookingSharePreference.getUserId())
    }

    private fun getDriverHistory() {
        mView!!.showLoading()
        val disposable = RetrofitManager.getDriverHistory(object : ICallBack<BaseResponse<List<DriverHistoryResponse>>> {
            override fun onSuccess(result: BaseResponse<List<DriverHistoryResponse>>?) {
                mView!!.showDriverHistory(result?.data!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
            }

        }, CarBookingSharePreference.getUserId())
    }

}