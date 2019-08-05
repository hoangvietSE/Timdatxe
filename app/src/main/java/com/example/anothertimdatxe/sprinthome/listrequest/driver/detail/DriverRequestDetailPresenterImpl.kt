package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse

class DriverRequestDetailPresenterImpl(mView: DriverRequestDetailView) : BasePresenterImpl<DriverRequestDetailView>(mView), DriverRequestDetailPresenter {
    override fun getDataUserPost(id: Int) {
        mView!!.showLoading()
        val disposable = RetrofitManager.userPostDetail(object : ICallBack<BaseResult<UserPostDetailResponse>> {
            override fun onSuccess(result: BaseResult<UserPostDetailResponse>?) {
                mView!!.hideLoading()
                result?.data?.let {
                    mView!!.showDetailUserPost(it)
                }
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        }, id)
    }
}