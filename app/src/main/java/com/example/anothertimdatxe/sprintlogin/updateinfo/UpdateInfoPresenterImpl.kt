package com.example.anothertimdatxe.sprintlogin.updateinfo

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.application.CarBookingApplication
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.entity.response.DriverUpdateInfoResponse
import com.example.anothertimdatxe.entity.response.UserUpdateInfoResponse
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class UpdateInfoPresenterImpl(mView: UpdateInfoView) : BasePresenterImpl<UpdateInfoView>(mView), UpdateInfoPresenter {
    override fun updateInfo(userData: UserData) {
        if(userData.fullName!!.isEmpty()||userData.fullName!!.isNullOrBlank()){
            mView!!.onFullNameError()
        }else{
            if (CarBookingSharePreference.getUserData()!!.isUser) {
                userUpdateInfo(userData)
            } else {
                driverUpdateInfo(userData)
            }
        }
    }

    fun userUpdateInfo(userData: UserData) {
        mView!!.showLoading()
        val disposable = RetrofitManager.userUpdateInfo(object : ICallBack<BaseResult<UserUpdateInfoResponse>> {
            override fun onSuccess(result: BaseResult<UserUpdateInfoResponse>?) {
                mView!!.hideLoading()
                userData.fullName = result?.data!!.fullName!!
                userData.birthday = result?.data!!.birthday!!
                userData.gender = result?.data!!.gender!!.toString()
                CarBookingSharePreference.setUserData(userData)
                mView!!.onUpdateSuccess()
            }

            override fun onError(e: ApiException) {
                handleError(e)
            }

        }, userData)
        addDispose(disposable)
    }

    fun driverUpdateInfo(userData: UserData) {
        mView!!.showLoading()
        val disposable = RetrofitManager.driverUpdateInfo(object : ICallBack<BaseResult<DriverUpdateInfoResponse>> {
            override fun onSuccess(result: BaseResult<DriverUpdateInfoResponse>?) {
                mView!!.hideLoading()
                userData.fullName = result?.data!!.fullName!!
                userData.birthday = result?.data!!.birthday!!
                userData.gender = result?.data!!.gender!!.toString()
                CarBookingSharePreference.setUserData(userData)
                mView!!.onUpdateSuccess()
            }

            override fun onError(e: ApiException) {
                handleError(e)
            }

        }, userData)
    }

    private fun handleError(e: ApiException) {
        if (e.mThrowable is ConnectException || e.mThrowable is UnknownHostException || e.mThrowable is TimeoutException) {
            ToastUtil.show(CarBookingApplication.instance.resources.getString(R.string.server_error))
        }
    }
}