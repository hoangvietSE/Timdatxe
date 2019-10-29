package com.example.anothertimdatxe.presentation.book.user

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.DriverPostDetailResponse
import com.example.anothertimdatxe.util.ToastUtil

class UserBookDetailPresenterImpl(mView: UserBookDetailView) : BasePresenterImpl<UserBookDetailView>(mView), UserBookDetailPresenter {
    override fun fetchUserBookDetail(postId: Int) {
        mView!!.showLoading()
        addDispose(RetrofitManager.getUserBookDetail(postId, object : ICallBack<BaseResult<DriverPostDetailResponse>> {
            override fun onSuccess(result: BaseResult<DriverPostDetailResponse>?) {
                mView!!.hideLoading()
                mView!!.showUserBookDetail(result?.data!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message!!)
            }

        }))
    }

}