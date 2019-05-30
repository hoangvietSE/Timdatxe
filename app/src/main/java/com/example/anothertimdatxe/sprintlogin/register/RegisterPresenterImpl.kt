package com.example.anothertimdatxe.sprintlogin.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.RegisResult
import com.example.anothertimdatxe.request.RegisterRequest
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.util.MyApp
import kotlin.math.log

class RegisterPresenterImpl(mView: RegisterView) : BasePresenterImpl<RegisterView>(mView), RegisterPresenter {
    override fun registerDriver(request: RegisterRequest) {
        mView!!.showLoading()
        RetrofitManager.registerDriver(object : ICallBack<BaseResult<RegisResult>> {
            override fun onSuccess(result: BaseResult<RegisResult>?) {
                if (result?.data != null) {
                    mView!!.goToConfirm(result?.data!!.token_register!!, MyApp.KEY_REGISTER_DRIVER)
                }
                //Log.d("myLog", result?.msg)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, e.apiError(), LENGTH_LONG).show()
                mView!!.hideLoading()
            }

        }, request)
    }

    override fun registerUser(request: RegisterRequest) {

    }
}