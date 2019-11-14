package com.example.anothertimdatxe.sprinthome.listpost.driver

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NetworkUtil

class DriverListPostPresenterImpl(mView: DriverListPostView) : BasePresenterImpl<DriverListPostView>(mView), DriverListPostPresenter {
    companion object {
        const val LIMIT = 6
    }

    private var pageIndex = 1
    private var total = 0
    private var startTime = ""
    private var startPoint = ""
    private var endPoint = ""
    private var status: Int? = null

    override fun setSpinnerStatus() {
        var mListStatus: List<String> = listOf(
                "Chọn trạng thái",
                "Đang chờ phê duyệt",
                "Công khai",
                "Chuyến đi đã kết thúc",
                "Hủy bỏ")
        mView!!.setSpinnerStatus(mListStatus)
    }

    override fun refreshList() {
        pageIndex = 1
        total = 0
        fetListDriverPost()
    }

    override fun fetchListDriverPost(date: String) {
        pageIndex = 1
        total = 0
        if (date.length > 0) {
            startTime = DateUtil.formatDate(date, DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
        } else {
            startTime = date
        }
        fetListDriverPost()
    }

    override fun fetchListDriverPost(status: Int?) {
        pageIndex = 1
        total = 0
        this.status = status
        fetListDriverPost()
    }

    override fun fetListDriverPost() {
        var data: MutableMap<String, Any> = mutableMapOf()
        data["page"] = pageIndex
        data["limit"] = LIMIT
        data["start_time"] = startTime
        data["start_point"] = startPoint
        data["end_point"] = endPoint
        status?.let {
            data["status"] = it
        }
        val disposable = RetrofitManager.driverListPost(data)
                .doOnSubscribe {
                    if (pageIndex == 1) mView!!.showPreview()
                }
                .doFinally {
                    mView!!.hidePreview()
                    if (pageIndex <= total) {
                        mView!!.enableLoadingMore(true)
                    } else {
                        mView!!.enableLoadingMore(false)
                    }
                }
                .subscribe(
                        {
                            total = it.total_page!!
                            pageIndex++
                            mView!!.setListItem(it.data!!)
                            mView!!.showNoResult(it.data!!.size == 0)
                        },
                        {
                            handleError(it)
                        }
                )
        addDispose(disposable)
    }

    fun handleError(it: Throwable) {
        NetworkUtil.handleError(it)
    }

}