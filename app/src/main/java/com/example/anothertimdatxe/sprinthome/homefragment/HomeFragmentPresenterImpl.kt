package com.example.anothertimdatxe.sprinthome.homefragment

import android.util.Log
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.util.NetworkUtil
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class HomeFragmentPresenterImpl(mView: HomeFragmentView) : BasePresenterImpl<HomeFragmentView>(mView), HomeFragmentPresenter {
    override fun getData(isRefreshing: Boolean) {
        getHotCities()
        getHotBanners()
        getPost(isRefreshing)
    }

    private fun getPost(isRefreshing: Boolean) {
        //UserPostAndDriverPostResponse : Result value
        val disposable = Single.zip(RetrofitManager.userPostHome(), RetrofitManager.driverPostHome(),
                BiFunction<BaseResult<List<UserPostResponse>>, BaseResult<List<DriverPostResponse>>, UserPostAndDriverPostResponse> { userPost, driverPost ->
                    UserPostAndDriverPostResponse(userPost, driverPost)
                }).doOnSubscribe {
            mView!!.showLoadingData()
        }
                .doFinally {
                    mView!!.hideLoadingData()
                }
                .subscribe(
                        {
                            if (it.userPost.status == 200 && it.driverPost.status == 200) {
                                mView!!.showListUserPost(it.userPost.data!!, isRefreshing)
                                mView!!.showListDriverPost(it.driverPost.data!!, isRefreshing)
                            }
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                )
        addDispose(disposable)
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

    class UserPostAndDriverPostResponse(
            val userPost: BaseResult<List<UserPostResponse>>,
            val driverPost: BaseResult<List<DriverPostResponse>>
    )

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