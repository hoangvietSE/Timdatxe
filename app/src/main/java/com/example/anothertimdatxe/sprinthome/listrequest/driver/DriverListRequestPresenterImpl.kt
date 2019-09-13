package com.example.anothertimdatxe.sprinthome.listrequest.driver

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NetworkUtil

class DriverListRequestPresenterImpl(mView: DriverListRequestView) : BasePresenterImpl<DriverListRequestView>(mView), DriverListRequestPresenter {
    companion object {
        const val LIMIT = 6
    }

    private var pageIndex = 1
    private var total = 0
    private var bookTime = ""
    private var startPoint = ""
    private var endPoint = ""
    private var status: Int? = null

    override fun setSpinnerStatus() {
        var mListStatus: List<String> = listOf(
                "Chọn trạng thái",
                "Đang chờ",
                "Được chấp nhận",
                "Bị từ chối",
                "Chuyến đi đã kết thúc",
                "Hủy bỏ")
        mView!!.setSpinnerStatus(mListStatus)
    }

    override fun refreshList() {
        pageIndex = 1
        total = 0
        fetListDriverBook()
    }

    override fun fetchListDriverBook(date: String) {
        pageIndex = 1
        total = 0
        if (date.length > 0) {
            bookTime = DateUtil.formatDate(date, DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
        } else {
            bookTime = date
        }
        fetListDriverBook()
    }

    override fun fetchListDriverBook(status: Int?) {
        pageIndex = 1
        total = 0
        this.status = status
        fetListDriverBook()
    }

    override fun fetListDriverBook() {
        var data: MutableMap<String, Any> = mutableMapOf()
        data["page"] = pageIndex
        data["limit"] = LIMIT
        data["book_time"] = bookTime
        data["start_point"] = startPoint
        data["end_point"] = endPoint
        status?.let {
            data["status"] = it
        }
        val disposable = RetrofitManager.fetchListDriverBook(data)
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