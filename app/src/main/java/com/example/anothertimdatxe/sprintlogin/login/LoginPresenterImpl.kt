package com.example.anothertimdatxe.sprintlogin.login

import android.content.Context
import android.widget.Toast
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.request.LoginFacebookRequest
import com.example.anothertimdatxe.request.LoginRequest
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG

class LoginPresenterImpl(mView: LoginView) : BasePresenterImpl<LoginView>(mView), LoginPresenter {
    override fun login(email: String, password: String) {
        //do-something
    }

    override fun loginUser(email: String, password: String) {
        //Request Object will be pass into @Body of request method
        mView!!.showLoading()
        val request = LoginRequest()
        request.email = email
        request.password = password
        request.remember = 1
        var disposable = RetrofitManager.loginUser(object : ICallBack<BaseResult<UserData>> {
            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, e.apiError(), LENGTH_LONG).show()
                mView!!.hideLoading()
            }

            override fun onSuccess(result: BaseResult<UserData>?) {
                Toast.makeText(mView as Context, R.string.login_success, LENGTH_LONG).show()
                mView!!.hideLoading()
                result?.data!!.isUser = true
                CarBookingSharePreference.setUserData(result?.data!!)
                if(result?.data!!.full_name.isNullOrEmpty()){
                    mView!!.goToUpdateInfo()
                }
                mView!!.goToNextScreen()
            }
        }, request)

        addDispose(disposable)
    }

    override fun loginDriver(email: String, password: String) {
        mView!!.showLoading()
        val request = LoginRequest()
        request.email = email
        request.password = password
        request.remember = 1
        var disposable = RetrofitManager.loginDriver(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                Toast.makeText(mView as Context, R.string.login_success, LENGTH_LONG).show()
                mView!!.hideLoading()
                result?.data!!.isDriver = true
                CarBookingSharePreference.setUserData(result?.data!!)
                if(result?.data!!.full_name.isNullOrEmpty()){
                    mView!!.goToUpdateInfo()
                }
                mView!!.goToNextScreen()
            }

            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, e.apiError(), LENGTH_LONG).show()
                mView!!.hideLoading()
            }

        }, request)

        addDispose(disposable)
    }

    override fun loginSocial(socialId: String, full_name: String, email: String, socialType: String) {
        mView!!.showLoading()
        var request: LoginFacebookRequest = LoginFacebookRequest()
        request.social_id = socialId
        request.device = "android"
        request.device_token = ""
        request.email = email
        request.full_name = full_name
        request.social_type = socialType
        var disposable = RetrofitManager.loginSocial(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.hideLoading()
                result?.data?.let {
                    ToastUtil.show("Đăng nhập thành công!")
                    CarBookingSharePreference.setUserData(it)
                    mView!!.goToNextScreen()
                }
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message.toString())
            }

        }, request)
        addDispose(disposable)
    }

}