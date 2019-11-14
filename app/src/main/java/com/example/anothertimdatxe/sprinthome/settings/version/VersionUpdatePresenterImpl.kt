package com.example.anothertimdatxe.sprinthome.settings.version

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.VersionAppResponse
import com.example.anothertimdatxe.util.ToastUtil

class VersionUpdatePresenterImpl(mView: VersionUpdateView) : BasePresenterImpl<VersionUpdateView>(mView),
        VersionUpdatePresenter {
    override fun getVersionApp() {
        val disposable = RetrofitManager.getVersionApp(object : ICallBack<BaseResult<List<VersionAppResponse>>> {
            override fun onSuccess(result: BaseResult<List<VersionAppResponse>>?) {
                mView!!.showVersionApp(result?.data!!)
            }

            override fun onError(e: ApiException) {
                ToastUtil.show(e.message!!)
            }

        })
    }

}