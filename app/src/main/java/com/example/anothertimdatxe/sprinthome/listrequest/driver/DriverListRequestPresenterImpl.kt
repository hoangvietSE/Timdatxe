package com.example.anothertimdatxe.sprinthome.listrequest.driver

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
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
    private var status = 0

    override fun setSpinnerStatus() {
        var mListStatus: List<String> = listOf(
                "Chọn trạng thái",
                "Đang chờ phê duyệt",
                "Công khai",
                "Chuyến đi đã kết thúc",
                "Hủy bỏ")
        mView!!.setSpinnerStatus(mListStatus)
    }

    override fun fetListDriverBook() {
        var data: MutableMap<String, Any> = mutableMapOf()
        data["page"] = pageIndex
        data["limit"] = LIMIT
        data["book_time"] = bookTime
        data["start_point"] = startPoint
        data["end_point"] = endPoint
        val disposable = RetrofitManager.fetchListDriverBook(data)
                .doOnSubscribe {
                    mView!!.showPreview()
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
                            total = it.total!!
                            pageIndex++
                            mView!!.setListItem(it.data!!)
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