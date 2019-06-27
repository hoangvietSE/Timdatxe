package com.example.anothertimdatxe.sprinthome.userpostcreated

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserListPostEntity

class UserPostCreatedPresenterImpl(mView: UserPostCreatedView) : BasePresenterImpl<UserPostCreatedView>(mView), UserPostCreatedPresenter {
    override fun getData() {
        getListUserPostCreated()
    }

    override fun getListUserPostCreated() {
        val disposable = RetrofitManager.getUserPostCreated(object : ICallBack<BaseResult<List<UserListPostEntity>>> {
            override fun onSuccess(result: BaseResult<List<UserListPostEntity>>?) {
                mView!!.showListUserPostCreated(result?.data!!)
            }

            override fun onError(e: ApiException) {
            }

        })
        addDispose(disposable)
    }
}