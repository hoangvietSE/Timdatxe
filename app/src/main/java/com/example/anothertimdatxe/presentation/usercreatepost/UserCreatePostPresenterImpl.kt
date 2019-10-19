package com.example.anothertimdatxe.presentation.usercreatepost

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.UserCreatePostResponse
import com.example.anothertimdatxe.request.UserCreatePostRequest
import com.example.anothertimdatxe.util.NumberUtil
import com.google.android.gms.maps.model.LatLng

class UserCreatePostPresenterImpl(mView: UserCreatePostView) : BasePresenterImpl<UserCreatePostView>(mView), UserCreatePostPresenter {
    override fun createPost(data: UserCreatePostRequest) {
        if (data.title.isNullOrEmpty()) {
            mView!!.onTitleEmpty()
            return
        }
        if (NumberUtil.isNumberString(data.title!!)) {
            mView!!.onTitleError()
            return
        }
        if (data.number_seat == 0) {
            mView!!.onSeatError()
            return
        }
        mView!!.showLoading()
        RetrofitManager.userCreatePost(data, object : ICallBack<BaseResult<UserCreatePostResponse>> {
            override fun onSuccess(result: BaseResult<UserCreatePostResponse>?) {
                mView!!.hideLoading()
                mView!!.onCreatePostSuccess()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        })
    }

    override fun getWayPoints(data: UserCreatePostRequest, listWayPoints: ArrayList<LatLng>) {
        var wayPointBuilder = StringBuilder("")
        listWayPoints.forEachIndexed { index, latLng ->
            if (index != listWayPoints.size - 1) {
                wayPointBuilder.append("${latLng.latitude},${latLng.longitude},")
            } else {
                wayPointBuilder.append("${latLng.latitude},${latLng.longitude}")
            }
        }
        mView!!.showWayPoints(data, wayPointBuilder.toString())
    }
}