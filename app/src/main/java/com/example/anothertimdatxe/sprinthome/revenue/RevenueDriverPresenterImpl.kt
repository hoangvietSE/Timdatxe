package com.example.anothertimdatxe.sprinthome.revenue

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.BaseRevenueResponse
import com.example.anothertimdatxe.entity.response.DriverRevenueResponse

class RevenueDriverPresenterImpl(mView: RevenueDriverView) : BasePresenterImpl<RevenueDriverView>(mView), RevenueDriverPresenter {
    override fun fetchData(month: Int) {
        mView!!.showLoading()
        val disposable = RetrofitManager.getDriverRevenue(object : ICallBack<BaseRevenueResponse<List<DriverRevenueResponse>>> {
            override fun onSuccess(result: BaseRevenueResponse<List<DriverRevenueResponse>>?) {
                mView!!.hideLoading()
                mView!!.showDetail(result?.total_number_of_trips!!, result?.total_money!!)
                mView!!.showDataByMonth(result?.data!!)
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        }, month)
        addDispose(disposable)
    }
}