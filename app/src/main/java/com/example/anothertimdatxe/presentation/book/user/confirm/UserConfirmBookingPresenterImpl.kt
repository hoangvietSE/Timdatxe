package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse

class UserConfirmBookingPresenterImpl(mView: UserConfirmBookingView) : BasePresenterImpl<UserConfirmBookingView>(mView), UserConfirmBookingPresenter {
    override fun fetchDataBooking(driverPostId: Int) {
        mView!!.showLoading()
        addDispose(RetrofitManager.getUserConfirmBooking(driverPostId, object : ICallBack<BaseResult<ConfirmBookingResponse>> {
            override fun onSuccess(result: BaseResult<ConfirmBookingResponse>?) {
                mView!!.showDataBooking(result?.data!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        }))
    }

    override fun setSeatSpinner(emptySeat: Int?) {
        val data = mutableListOf<String>()
        for (i in 1..emptySeat!!) {
            data?.add(i.toString())
        }
        mView!!.addSeatToSpinner(data)
    }
}