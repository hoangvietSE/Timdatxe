package com.example.anothertimdatxe.sprinthome.help

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.ContactEntity
import com.example.anothertimdatxe.entity.response.ContactSystemResponse
import com.example.anothertimdatxe.request.ContactRequest

class SupportPresenterImpl(mView: SupportView) : BasePresenterImpl<SupportView>(mView), SupportPresenter {
    override fun sendSupport(data: ContactEntity) {
        if (data.phone.isNullOrEmpty() || data.phone.isNullOrBlank()) {
            mView!!.onPhoneError()
        } else if (data.email.isNullOrEmpty() || data.email.isNullOrBlank()) {
            mView!!.onEmailError()
        } else if (data.address.isNullOrEmpty() || data.address.isNullOrBlank()) {
            mView!!.onAddressError()
        } else if (data.content.isNullOrEmpty() || data.content.isNullOrBlank()) {
            mView!!.onEdittextError()
        } else {
            mView!!.showLoading()
            val request = ContactRequest()
            request.title = data.email
            request.message = data.content
            val disposable = RetrofitManager.sendContactSystem(object : ICallBack<BaseResult<ContactSystemResponse>> {
                override fun onSuccess(result: BaseResult<ContactSystemResponse>?) {
                    mView!!.showContactSuccess()
                    mView!!.hideLoading()
                }

                override fun onError(e: ApiException) {
                    mView!!.showContactFail()
                    mView!!.hideLoading()
                }

            }, request)
            addDispose(disposable)
        }
    }

}