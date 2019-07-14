package com.example.anothertimdatxe.sprinthome.postmore

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.request.PostCreatedFindCarRequest
import com.example.anothertimdatxe.request.PostCreatedFindUserRequest

class PostCreatedMorePresenterImpl(mView: PostCreatedMoreView) : BasePresenterImpl<PostCreatedMoreView>(mView),
        PostCreatedMorePresenter {
    private var start_time: String = ""
    private var empty_seat: String = ""
    private var number_seat: String = ""
    private var status: String = ""
    private var start_point: String = ""
    private var end_point: String = ""
    private var total_page: Int = 0
    private var page: Int = 1
    private var page_search = 1

    override fun getListFindUser(isRefreshing: Boolean) {
        if (isRefreshing) page = 1//reset
        var data: MutableMap<String, Any> = mutableMapOf()
        data["start_time"] = start_time
        data["empty_seat"] = empty_seat
        data["start_point"] = start_point
        data["end_point"] = end_point
        data["page"] = page
        data["type"] = 0
        val disposable = RetrofitManager.getListPostFindUser(data)
                .doOnSubscribe {
                    if (page == 1)
                        if (!isRefreshing) mView!!.showLoadingProgress()
                }
                .doFinally {
                    mView!!.hideLoadingProgress()
                    mView!!.enableLoadingMore(page <= total_page)
                }
                .subscribe(
                        {
                            mView!!.showListPostFindUser(it.data!!, isRefreshing)
                            page++
                            total_page = it.total_page!!
                        },
                        {
                            return@subscribe
                        }
                )
    }

    override fun getListFindCar(isRefreshing: Boolean) {
        if (isRefreshing) page = 1//reset
        var data: MutableMap<String, Any> = mutableMapOf()
        data["start_time"] = start_time
        data["number_seat"] = number_seat
        data["start_point"] = start_point
        data["end_point"] = end_point
        data["page"] = page
        val disposable = RetrofitManager.getListPostFindCar(data)
                .doOnSubscribe {
                    if (page == 1)
                        if (!isRefreshing) mView!!.showLoadingProgress()
                }
                .doFinally {
                    mView!!.hideLoadingProgress()
                    mView!!.enableLoadingMore(page <= total_page)

                }
                .subscribe(
                        {
                            mView!!.showListPostFindCar(it.data!!, isRefreshing)
                            page++
                            total_page = it.total_page!!
                        },
                        {
                            return@subscribe
                        }
                )
    }

    override fun getListSearchFindUser(request: PostCreatedFindUserRequest) {
        var data: MutableMap<String, Any> = mutableMapOf()
        data["start_time"] = request.start_time!!
        data["empty_seat"] = initSeatValue(request.empty_seat!!)
        data["start_point"] = request.start_point!!
        data["end_point"] = request.end_point!!
        data["page"] = page_search
        data["type"] = 0
        val disposable = RetrofitManager.getListPostFindUser(data)
                .doOnSubscribe {
                    mView?.showLoading()
                }
                .doFinally {
                    mView?.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.showListSearchFindUser(it.data!!)
                        },
                        {
                            return@subscribe
                        }
                )
    }

    override fun getListSearchFindCar(request: PostCreatedFindCarRequest) {
        var data: MutableMap<String, Any> = mutableMapOf()
        data["start_time"] = request.start_time!!
        data["number_seat"] = initSeatValue(request.number_seat!!)
        data["start_point"] = request.start_point!!
        data["end_point"] = request.end_point!!
        data["page"] = page_search
        val disposable = RetrofitManager.getListPostFindCar(data)
                .doOnSubscribe {
                    mView?.showLoading()
                }
                .doFinally {
                    mView?.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.showListSearchFindCar(it.data!!)
                        },
                        {
                            return@subscribe
                        }
                )
    }

    fun initSeatValue(value: String): String {
        when (value) {
            "Chọn số ghế" -> {
                return ""
            }
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