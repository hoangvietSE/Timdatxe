package com.example.anothertimdatxe.sprinthome.settings.faqs

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.FaqsResponse

class FaqsPresenterImpl(mView: FaqsView) : BasePresenterImpl<FaqsView>(mView),
        FaqsPresenter {
    private var limit: Int = 5
    private var cat_id: Int = 1
    private var page: Int = 1
    override fun onGetFaqs() {
        mView!!.showLoading()
        var faqsRequest: MutableMap<String, Any> = mutableMapOf()
        faqsRequest["limit"] = limit
        faqsRequest["cat_id"] = cat_id
        faqsRequest["page"] = page
        var disposable = RetrofitManager.onGetFaqs(object : ICallBack<BaseResult<List<FaqsResponse>>> {
            override fun onSuccess(result: BaseResult<List<FaqsResponse>>?) {
                Toast.makeText(mView as Context, "Loading Success", LENGTH_LONG).show()
                mView!!.setView(result!!.data)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, "Loading Error", LENGTH_LONG).show()
                mView!!.hideLoading()
            }
        }, faqsRequest)
        addDispose(disposable)
    }
}
