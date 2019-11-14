package com.example.anothertimdatxe.sprinthome.listpost.user

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NetworkUtil

class UserListPostPresenterImpl(mView: UserListPostView) : BasePresenterImpl<UserListPostView>(mView), UserListPostPresenter {
    private var startTime: String = ""
    private var startPoint: String = ""
    private var endPoint: String = ""
    private var limit: Int = 10
    private var totalPage: Int = 0
    private var pageIndex = 1
    private var status: String = ""
    private var data: MutableMap<String, Any?> = mutableMapOf()

    override fun initSpinnerStatus() {
        val listOfStatus = listOf(
                "Chọn trạng thái",
                "Đang chờ phê duyệt",
                "Công khai",
                "Đã thỏa thuận với tài xê",
                "Kết thúc",
                "Hủy bỏ"
        )
        mView?.setSpinnerStatus(listOfStatus)
    }

    override fun refreshData() {
        resetData()
    }

    private fun resetData() {
        pageIndex = 1
        totalPage = 0
        fetchUserListPost()
    }

    override fun fetchUserListPost() {
        setQueryParams()
        addDispose(RetrofitManager.getUserListPost(data)
                .doOnSubscribe {
                    if (pageIndex == 1 && mView != null) {
                        mView!!.showRefreshing()
                    }
                }
                .doFinally {
                    mView!!.hideRefreshing()
                    if (pageIndex <= totalPage) {
                        mView!!.enableLoadingMore(true)
                    } else {
                        mView!!.enableLoadingMore(false)
                    }
                }
                .subscribe(
                        {
                            pageIndex++
                            totalPage = it.total_page!!
                            mView?.showListUserPost(it.data!!)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                ))
    }

    private fun setQueryParams() {
        data["start_time"] = startTime
        data["start_point"] = startPoint
        data["end_point"] = startPoint
        data["status"] = status
        data["limit"] = limit
        data["page"] = pageIndex

    }

    override fun setStatus(status: Int) {
        this.status = if (status == 0) {
            ""
        } else {
            (status - 1).toString()
        }
        resetData()
    }

    override fun setDate(date: String) {
        if (!date.isEmpty()) {
            startTime = DateUtil.formatDate(date, DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
        } else {
            startTime = ""
        }
        resetData()
    }
}