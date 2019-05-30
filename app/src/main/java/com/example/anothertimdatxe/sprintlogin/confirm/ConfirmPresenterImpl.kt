package com.example.anothertimdatxe.sprintlogin.confirm

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.request.ActiveRequest

class ConfirmPresenterImpl(mView: ConfirmView) : BasePresenterImpl<ConfirmView>(mView), ConfirmPresenter {
    override fun onActiveDriver(request: ActiveRequest) {
        RetrofitManager.activeDriver(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.hideLoading()
                mView!!.goToScreenHome()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                Toast.makeText(mView as Context, e.apiError(), LENGTH_LONG).show()
            }

        }, request)
    }

    override fun onActiveUser(request: ActiveRequest) {
    }

}