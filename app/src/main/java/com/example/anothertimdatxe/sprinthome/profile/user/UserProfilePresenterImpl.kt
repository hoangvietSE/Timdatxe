package com.example.anothertimdatxe.sprinthome.profile.user

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.eventbus.GetProfileSuccess
import org.greenrobot.eventbus.EventBus

class UserProfilePresenterImpl(mView: UserProfileView) : BasePresenterImpl<UserProfileView>(mView), UserProfilePresenter {
    override fun getUserData() {
        val disposable = RetrofitManager.getUserInfo(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                val userData = result?.data!!
                userData.count_books = result?.count_books
                mView!!.showData(userData)
                val getProfileSuccess = GetProfileSuccess(true)
                EventBus.getDefault().post(getProfileSuccess)
            }

            override fun onError(e: ApiException) {
                val getProfileSuccess = GetProfileSuccess(false)
                EventBus.getDefault().post(getProfileSuccess)
            }

        })
    }
}