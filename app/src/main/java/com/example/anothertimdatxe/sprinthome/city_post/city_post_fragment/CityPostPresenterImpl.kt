package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager

class CityPostPresenterImpl(mView: CityPostView) : BasePresenterImpl<CityPostView>(mView), CityPostPresenter {
    private var pageIndex = 1
    private var totalPage = 0

    override fun getUserCityPost(city: String) {
        val data: MutableMap<String, Any> = mutableMapOf()
        data["city"] = city
        data["page"] = pageIndex
        val disposable = RetrofitManager.getUserSearchCityPost(data)
                .doOnSubscribe {
                    if (mView != null) {
                        mView!!.showLoadingIndicatior()
                    }
                }
                .doFinally {
                    if (mView != null) {
                        mView!!.hideLoadingIndicator()
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView!!.showEmptyContent(it.data!!.size == 0)
                            mView!!.showUserCityPost(it.data!!)
                        },
                        {
                            //do-something
                        }
                )
        addDispose(disposable)
    }

    override fun getDriverCityPost(city: String) {
        val data: MutableMap<String, Any> = mutableMapOf()
        data["city"] = city
        data["page"] = pageIndex
        val disposable = RetrofitManager.getDriverSearchCityPost(data)
                .doOnSubscribe {
                    if (mView != null) {
                        mView!!.showLoadingIndicatior()
                    }
                }
                .doFinally {
                    if (mView != null) {
                        mView!!.hideLoadingIndicator()
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView!!.showEmptyContent(it.data!!.size == 0)
                            mView!!.showDriverCityPost(it.data!!)
                        },
                        {
                            //do-something
                        }
                )
    }
}