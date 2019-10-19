package com.example.anothertimdatxe.sprinthome.listrequest.user.detail

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.UserCancelUserBookResponse
import com.example.anothertimdatxe.entity.response.UserRequestDetailResponse
import com.example.anothertimdatxe.util.ToastUtil

class UserRequestDetailPresenterImpl(mView: UserRequestDetailView) : BasePresenterImpl<UserRequestDetailView>(mView), UserRequestDetailPresenter {
    override fun fetchUserRequestDetail(postId: Int) {
        mView!!.showLoading()
        RetrofitManager.fetchUserRequestDetail(postId, object : ICallBack<BaseResult<UserRequestDetailResponse>> {
            override fun onSuccess(result: BaseResult<UserRequestDetailResponse>?) {
                mView!!.hideLoading()
                mView!!.showUserRequestDetail(result?.data!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message!!)
            }

        })
    }

    override fun userCancelUserBook(userBookId: Int) {
        mView!!.showLoading()
        RetrofitManager.userCancelUserBook(userBookId, object : ICallBack<BaseResult<UserCancelUserBookResponse>> {
            override fun onSuccess(result: BaseResult<UserCancelUserBookResponse>?) {
                mView!!.hideLoading()
                mView!!.userCancelUserRequestSuccess(result?.msg!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                mView!!.userCancelUserRequestError()
            }

        })
    }
}