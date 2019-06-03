package com.example.anothertimdatxe.sprintlogin.updatepassword

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.request.ForgotRequest
import com.example.anothertimdatxe.request.UpdatePasswordRequest
import com.example.anothertimdatxe.util.ToastUtil

class UpdatePasswordPresenterImpl(mView: UpdatePasswordView) : BasePresenterImpl<UpdatePasswordView>(mView), UpdatePasswordPresenter {
    override fun userUpdatePassword(email: String, token: String, password: String) {
        mView!!.showLoading()
        val request: UpdatePasswordRequest = UpdatePasswordRequest(email, token, password)
        val disposable = RetrofitManager.userUpdatePassword(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.hideLoading()
                mView!!.showCompleteDiaglog()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show("Mã token không hợp lệ!")
            }

        }, request)
        addDispose(disposable)
    }

    override fun driverUpdatePassword(email: String, token: String, password: String) {
        mView!!.showLoading()
        val request: UpdatePasswordRequest = UpdatePasswordRequest(email, token, password)
        val disposable = RetrofitManager.driverUpdatePassword(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.hideLoading()
                mView!!.showCompleteDiaglog()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show("Mã token không hợp lệ!")
            }

        }, request)
        addDispose(disposable)
    }

    override fun userResendToken(email: String) {
        mView!!.showLoading()
        val request: ForgotRequest = ForgotRequest(email)
        val disposable = RetrofitManager.resetUserPassword(object : ICallBack<BaseResult<ForgotResult>> {
            override fun onSuccess(result: BaseResult<ForgotResult>?) {
                mView!!.hideLoading()
                mView!!.showAlertDialogSendTokenSuccess()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show("Email không hợp lệ!")
            }

        }, request)
    }

    override fun driverResendToken(email: String) {
        mView!!.showLoading()
        val request: ForgotRequest = ForgotRequest(email)
        val disposable = RetrofitManager.resetDriverPassword(object : ICallBack<BaseResult<ForgotResult>> {
            override fun onSuccess(result: BaseResult<ForgotResult>?) {
                mView!!.hideLoading()
                mView!!.showAlertDialogSendTokenSuccess()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show("Email không hợp lệ!")
            }

        }, request)
    }

}