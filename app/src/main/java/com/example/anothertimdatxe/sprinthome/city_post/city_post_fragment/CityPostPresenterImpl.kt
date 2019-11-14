package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager

class CityPostPresenterImpl(mView: CityPostView) : BasePresenterImpl<CityPostView>(mView), CityPostPresenter {
    private var pageIndex = 1
    private var totalPage = 0
    private val limit: Int = 6

    override fun getUserCityPost(city: String, isRefreshing: Boolean) {
        if (isRefreshing) pageIndex = 1
        val data: MutableMap<String, Any> = mutableMapOf()
        data["city"] = city
        data["page"] = pageIndex
        data["limit"] = limit
        val disposable = RetrofitManager.getUserSearchCityPost(data)
                .doOnSubscribe {
                    if (mView != null && pageIndex == 1 && !isRefreshing) {
                        mView!!.showLoadingIndicatior()
                    }
                }
                .doFinally {
                    if (mView != null) {
                        mView!!.hideLoadingIndicator()
                    }
                    if (pageIndex <= totalPage) {
                        mView!!.enableLoadingMore()
                    } else {
                        mView!!.disableLoadingMore()
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView!!.showEmptyContent(it.data!!.size == 0)
                            mView!!.showUserCityPost(it.data!!, isRefreshing)
                        },
                        {
                            //do-something
                        }
                )
        addDispose(disposable)
    }

    override fun getDriverCityPost(city: String, isRefreshing: Boolean) {
        if (isRefreshing) pageIndex = 1
        val data: MutableMap<String, Any> = mutableMapOf()
        data["city"] = city
        data["page"] = pageIndex
        data["limit"] = limit
        val disposable = RetrofitManager.getDriverSearchCityPost(data)
                .doOnSubscribe {
                    if (mView != null && pageIndex == 1 && !isRefreshing) {
                        mView!!.showLoadingIndicatior()
                    }
                }
                .doFinally {
                    if (mView != null) {
                        mView!!.hideLoadingIndicator()
                    }
                    if (pageIndex <= totalPage) {
                        mView!!.enableLoadingMore()
                    } else {
                        mView!!.disableLoadingMore()
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView!!.showEmptyContent(it.data!!.size == 0)
                            mView!!.showDriverCityPost(it.data!!, isRefreshing)
                        },
                        {
                            //do-something
                        }
                )
        addDispose(disposable)
    }
}