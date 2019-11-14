package com.example.anothertimdatxe.sprinthome.settings.condition

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.TermAndConditionResponse
import com.example.anothertimdatxe.util.ToastUtil


class ConditionPresenterImpl(mView: ConditionView) : BasePresenterImpl<ConditionView>(mView), ConditionPresenter {
    override fun getContentCondition() {
        mView!!.showLoading()
        val slug = "terms-and-condition"
        val disposable = RetrofitManager.getTermAndCondition(object : ICallBack<BaseResult<TermAndConditionResponse>> {
            override fun onSuccess(result: BaseResult<TermAndConditionResponse>?) {
                mView!!.hideLoading()
                mView!!.showContentCondition(result?.data!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                ToastUtil.show(e.message!!)
            }

        }, slug)
        addDispose(disposable)
    }

}