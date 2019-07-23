package com.example.anothertimdatxe.sprinthome.homefragment

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.HotCitiesResponse

class HomeFragmentPresenterImpl(mView: HomeFragmentView) : BasePresenterImpl<HomeFragmentView>(mView), HomeFragmentPresenter {
    override fun getData() {
        getHotCities()
    }

    private fun getHotCities() {
        val disposable = RetrofitManager.getListHotCities(object : ICallBack<BaseResult<List<HotCitiesResponse>>> {
            override fun onSuccess(result: BaseResult<List<HotCitiesResponse>>?) {
                mView!!.showListHotCities(result?.data!!)
            }

            override fun onError(e: ApiException) {
                //do-something
            }

        })
    }

}