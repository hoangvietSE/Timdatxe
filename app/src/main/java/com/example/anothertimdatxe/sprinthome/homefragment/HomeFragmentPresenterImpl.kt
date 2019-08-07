package com.example.anothertimdatxe.sprinthome.homefragment

import android.util.Log
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse

class HomeFragmentPresenterImpl(mView: HomeFragmentView) : BasePresenterImpl<HomeFragmentView>(mView), HomeFragmentPresenter {
    override fun getData() {
        getHotCities()
        getHotBanners()
    }

    private fun getHotBanners() {
        val disposable = RetrofitManager.getBanners(object : ICallBack<BaseResult<List<BannerHomeResponse>>> {
            override fun onSuccess(result: BaseResult<List<BannerHomeResponse>>?) {
                mView!!.showListBanners(result?.data!!)
            }

            override fun onError(e: ApiException) {
                Log.d("myLOG", "Error")
            }

        })
        addDispose(disposable)
    }

    private fun getHotCities() {
        val disposable = RetrofitManager.getListHotCities(object : ICallBack<BaseResult<ArrayList<HotCitiesResponse>>> {
            override fun onSuccess(result: BaseResult<ArrayList<HotCitiesResponse>>?) {
                mView!!.showListHotCities(result?.data!!)
            }

            override fun onError(e: ApiException) {
                //do-something
                Log.d("myLOG", "Error")
            }

        })
        addDispose(disposable)
    }

}