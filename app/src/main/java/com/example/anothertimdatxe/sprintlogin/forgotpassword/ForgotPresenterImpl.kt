package com.example.anothertimdatxe.sprintlogin.forgotpassword

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.request.ForgotRequest

class ForgotPresenterImpl(mView: ForgotView) : BasePresenterImpl<ForgotView>(mView), ForgotPresenter {
    override fun onUserForgotPassword(email: String) {
        mView!!.showLoading()
        var disposable = RetrofitManager.resetUserPassword(object : ICallBack<BaseResult<ForgotResult>> {
            override fun onSuccess(result: BaseResult<ForgotResult>?) {
                mView!!.hideLoading()
                var token: String = result?.data!!.token
                mView!!.goToUpdate(token)
                Log.d("myLog", token)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                Toast.makeText(mView as Context, e.message, LENGTH_LONG).show()
            }

        }, ForgotRequest(email))
    }

    override fun onDriverForgotPassword(email: String) {
        var disposable = RetrofitManager.resetDriverPassword(object : ICallBack<BaseResult<ForgotResult>> {
            override fun onSuccess(result: BaseResult<ForgotResult>?) {
                mView!!.hideLoading()
                var token: String = result?.data!!.token
                mView!!.goToUpdate(token)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                Toast.makeText(mView as Context, e.message, LENGTH_LONG).show()
            }

        }, ForgotRequest(email))
    }

}