package com.example.anothertimdatxe.sprintlogin.login

import android.content.Context
import android.widget.Toast
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.request.LoginRequest
import com.example.anothertimdatxe.util.CarBookingSharePreference
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
                Toast.makeText(mView as Context, "Login Success", LENGTH_LONG).show()
                mView!!.hideLoading()
                CarBookingSharePreference.setUserData(result?.data!!)
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
                Toast.makeText(mView as Context, "Login Success", LENGTH_LONG).show()
                mView!!.hideLoading()
                CarBookingSharePreference.setUserData(result?.data!!)
            }

            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, e.apiError(), LENGTH_LONG).show()
                mView!!.hideLoading()
            }

        }, request)

        addDispose(disposable)
    }
}