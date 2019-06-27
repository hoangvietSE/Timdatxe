package com.example.anothertimdatxe.sprinthome.profile.user

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.event_bus.GetProfileSuccess
import org.greenrobot.eventbus.EventBus

class UserProfilePresenterImpl(mView: UserProfileView) : BasePresenterImpl<UserProfileView>(mView), UserProfilePresenter {
    override fun getUserData() {
        val disposable = RetrofitManager.getUserInfo(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.showData(result?.data!!)
                EventBus.getDefault().post(GetProfileSuccess(true))
            }

            override fun onError(e: ApiException) {
                EventBus.getDefault().post(GetProfileSuccess(false))
            }

        })
    }
}