package com.example.anothertimdatxe.presentation.drivercreatepost

import android.util.Log
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.DriverCarResponse
import com.example.anothertimdatxe.entity.response.DriverCreatePostResponse
import com.example.anothertimdatxe.entity.response.DriverPostDetailResponse
import com.example.anothertimdatxe.request.DriverCreatePostRequest
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NetworkUtil.handleError
import com.example.anothertimdatxe.util.NumberUtil
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class DriverCreatePostPresenterImpl(mView: DriverCreatePostView) : BasePresenterImpl<DriverCreatePostView>(mView), DriverCreatePostPresenter {
    override fun fetchDriverCarInfo() {
        val disposable = RetrofitManager.driverCarInfo(object : ICallBack<BaseResult<List<DriverCarResponse>>> {
            override fun onSuccess(result: BaseResult<List<DriverCarResponse>>?) {
                mView!!.initSpinner(result?.data!!)
            }

            override fun onError(e: ApiException) {
            }

        })
        addDispose(disposable)
    }

    override fun driverCreatePost(request: DriverCreatePostRequest) {
        if (request.title.isNullOrEmpty()!!) {
            mView!!.onErrorNoTitle()
            return
        }
        if (NumberUtil.isNumberString(request.title!!)) {
            mView!!.onErrorTitleHaveNumber()
            return
        }
        if (request.date.isNullOrEmpty()!!) {
            mView!!.onErrorNoDate()
            return
        }
        if (request.time.isNullOrEmpty()!!) {
            mView!!.onErrorNoTime()
            return
        }
        setTime(request)
        if (validateStartingTime(request.start_time!!)) {
            mView!!.onErrorDateInPast()
            return
        }
        if (request.duration_time == null) {
            mView!!.onErrorNoEstimate()
            return
        }
        if (request.duration_time!! <= 0) {
            mView!!.onErrorInvalidEstimate()
            return
        }
        if (request.car_id?.toInt() == -1) {
            mView!!.onErrorNoCarBrand()
            return
        }
        when (request.type) {
            DriverCreatePostActivity.ITEM_TYPE_CONVINENT -> {
                if (request.regular_price.isNullOrEmpty() || request.price_level_1.isNullOrEmpty() || request.price_level_2.isNullOrEmpty() || request.price_level_3.isNullOrEmpty()) {
                    mView!!.onErrorNoMoney()
                    return
                }
                val price30Percent = request.price_level_1!!.toInt()
                val price50Percent = request.price_level_2!!.toInt()
                val price70Percent = request.price_level_3!!.toInt()
                val priceAll = request.regular_price!!.toInt()
                if (price30Percent > price50Percent || price30Percent > price70Percent || price30Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (price50Percent > price70Percent || price50Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (price70Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (price30Percent < 10000 || price50Percent < 10000 || price70Percent < 10000 || priceAll < 10000) {
                    mView!!.onErrorMinMoney()
                    return
                }
            }
            DriverCreatePostActivity.ITEM_TYPE_PRIVATE -> {
                if (request.private_price_1.isNullOrEmpty() || request.private_price_2.isNullOrEmpty()) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                val privatePrice50Percent = request.private_price_1!!.toInt()
                val privatePriceAll = request.private_price_2!!.toInt()
                if (privatePrice50Percent > privatePriceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (privatePrice50Percent < 10000 || privatePriceAll < 10000) {
                    mView!!.onErrorMinMoney()
                    return
                }
            }
            DriverCreatePostActivity.ITEM_TYPE_BOTH -> {
                if (request.regular_price.isNullOrEmpty() || request.price_level_1.isNullOrEmpty() || request.price_level_2.isNullOrEmpty() || request.price_level_3.isNullOrEmpty()
                        || request.private_price_1.isNullOrEmpty() || request.private_price_2.isNullOrEmpty()) {
                    mView!!.onErrorNoMoney()
                    return
                }
                val price30Percent = request.price_level_1!!.toInt()
                val price50Percent = request.price_level_2!!.toInt()
                val price70Percent = request.price_level_3!!.toInt()
                val priceAll = request.regular_price!!.toInt()
                val privatePrice50Percent = request.private_price_1!!.toInt()
                val privatePriceAll = request.private_price_2!!.toInt()
                if (price30Percent > price50Percent || price30Percent > price70Percent || price30Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (price50Percent > price70Percent || price50Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (price70Percent > priceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (privatePrice50Percent > privatePriceAll) {
                    mView!!.onErrorInvalidMoney()
                    return
                }
                if (privatePrice50Percent < 10000 || privatePriceAll < 10000) {
                    mView!!.onErrorMinMoney()
                    return
                }
                if (price30Percent < 10000 || price50Percent < 10000 || price70Percent < 10000 || priceAll < 10000) {
                    mView!!.onErrorMinMoney()
                    return
                }
            }
        }
        mView!!.showLoading()
        val disposable = RetrofitManager.driverCreatePost(object : ICallBack<BaseResult<DriverCreatePostResponse>> {
            override fun onSuccess(result: BaseResult<DriverCreatePostResponse>?) {
                mView!!.hideLoading()
                mView!!.onSuccessCreatePost()
            }

            override fun onError(e: ApiException) {
                Log.d("myLog", "Fail")
            }

        }, request)
        addDispose(disposable)
    }

    override fun fetData(id: Int) {
        val disposable = Single.zip(
                RetrofitManager.driverCarInfoV1(),
                RetrofitManager.driverPostDetailV1(id),
                BiFunction<
                        BaseResult<List<DriverCarResponse>>,
                        BaseResult<DriverPostDetailResponse>,
                        Pair<BaseResult<List<DriverCarResponse>>,BaseResult<DriverPostDetailResponse>>> { carInfoResponse, driverPostDetailResponse ->
                    Pair(carInfoResponse, driverPostDetailResponse)
                }
        )
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.initSpinner(it.first.data!!)
                            mView!!.showDataCreatedPost(it.second.data!!)
                        },
                        {
                            handleError(it)
                        }
                )
        addDispose(disposable)
    }

    private fun setTime(request: DriverCreatePostRequest) {
        request.start_time = DateUtil.formatDate(request.date!!, DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1) + " " + request.time?.trim() + ":00"
    }

    fun validateStartingTime(startTime: String): Boolean {
        val calendar = Calendar.getInstance()
        val currentInCalendar = Calendar.getInstance(TimeZone.getDefault())
        val date = DateUtil.formatStringToDate(startTime, DateUtil.DATE_FORMAT_13)
        calendar.time = date
        if (calendar.before(currentInCalendar) || calendar.equals(currentInCalendar)) {
            return true
        }
        return false
    }
}