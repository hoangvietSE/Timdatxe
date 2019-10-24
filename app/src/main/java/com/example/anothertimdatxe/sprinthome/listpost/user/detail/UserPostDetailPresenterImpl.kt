package com.example.anothertimdatxe.sprinthome.listpost.user.detail

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.PostDetailResponse
import com.example.anothertimdatxe.util.ToastUtil

class UserPostDetailPresenterImpl(mView: UserPostDetailView) : BasePresenterImpl<UserPostDetailView>(mView), UserPostDetailPresenter {
    override fun fetchUserPostDetail(userPostId: Int) {
        mView!!.showLoading()
        addDispose(RetrofitManager.getUserPostDetail(userPostId, object : ICallBack<BaseResult<PostDetailResponse>> {
            override fun onSuccess(result: BaseResult<PostDetailResponse>?) {
                mView!!.hideLoading()
                mView!!.showUserPostDetail(result?.data!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message!!)
            }

        }))
    }

    override fun deleteUserPost(userPostId: Int) {
        mView!!.showLoading()
        RetrofitManager.deleteUserPost(userPostId, object : ICallBack<BaseResult<PostDetailResponse>> {
            override fun onSuccess(result: BaseResult<PostDetailResponse>?) {
                mView!!.hideLoading()
                mView!!.onDeleteUserPostSuccess()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message!!)
            }

        })
    }
}