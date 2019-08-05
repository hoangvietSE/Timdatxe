package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.request.DriverSearchRequest
import com.example.anothertimdatxe.util.NetworkUtil

class DriverSearchPresenterImpl(mView: DriverSearchView) : BasePresenterImpl<DriverSearchView>(mView), DriverSearchPresenter {
    private var start_time: String = ""
    private var number_seat: String = ""
    private var start_point: String = ""
    private var end_point: String = ""
    private var page: Int = 1
    private var totalPage: Int = 0
    private var isSearch: Boolean = false

    override fun fetchDataLoadMore() {
        fetchData()
    }

    override fun fetchDataSearch(request: DriverSearchRequest) {
        start_time = request.date
        number_seat = initSeatNumber(request.seatNumber)
        start_point = request.startingPoint
        end_point = request.endingPoint
        page = 1
        totalPage = 0
        isSearch = true
        fetchData()
    }

    override fun fetchData() {
        val data: MutableMap<String, Any> = mutableMapOf()
        data["start_time"] = start_time
        data["number_seat"] = number_seat
        data["start_point"] = start_point
        data["end_point"] = end_point
        data["page"] = page
        val disposable = RetrofitManager.driverSearchUserPost(data)
                .doOnSubscribe {
                    if (mView != null && page == 1) mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                    if (page <= totalPage) {
                        mView!!.enableLoadingMore(true)
                    } else {
                        mView!!.enableLoadingMore(false)
                    }
                    isSearch = false
                }
                .subscribe(
                        {
                            page++
                            totalPage = it.total_page!!
                            mView!!.showNoResult(it.total == 0)
                            if (isSearch) {
                                mView!!.showListDriverSearchOnClick(it.data!!)
                            } else {
                                mView!!.showListDriverSearch(it.data!!)

                            }
                        },
                        {
                            handleError(it)
                        }
                )
        addDispose(disposable)
    }

    fun handleError(throwable: Throwable) {
        NetworkUtil.handleError(throwable)
    }

    fun initSeatNumber(value: String): String {
        when (value) {
            "Từ 1 đến 10 ghế" -> {
                return "1,10"
            }
            "Từ 10 đến 20 ghế" -> {
                return "10,20"
            }
            "Lớn hơn 20 ghế" -> {
                return "20,100"
            }
        }
        return ""
    }

}