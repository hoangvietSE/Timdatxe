package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse
import com.example.anothertimdatxe.request.DriverBookUserPostRequest
import com.example.anothertimdatxe.request.DriverFinishTripRequest
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.NetworkUtil
import com.example.anothertimdatxe.util.ToastUtil

class DriverRequestDetailPresenterImpl(mView: DriverRequestDetailView) : BasePresenterImpl<DriverRequestDetailView>(mView), DriverRequestDetailPresenter {
    private var canBook = 0
    override fun getDataUserPost(id: Int) {
        mView!!.showLoading()
        val disposable = RetrofitManager.userPostDetail(object : ICallBack<BaseResult<UserPostDetailResponse>> {
            override fun onSuccess(result: BaseResult<UserPostDetailResponse>?) {
                mView!!.hideLoading()
                result?.data?.let {
                    mView!!.showDetailUserPost(it)
                }
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        }, id)
    }

    override fun cancelRequest(driverBookOptionId: Int) {
        addDispose(RetrofitManager.driverCancelRequest(driverBookOptionId)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.finishScreen()
                            mView!!.cancelRequestSuccess(true)
                        },
                        {
                            mView!!.cancelRequestSuccess(false)
                            NetworkUtil.handleError(it)
                        }
                ))
    }

    override fun cancelBooking(driverBookId: Int) {
        addDispose(RetrofitManager.driverCancelBooking(driverBookId)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.finishScreen()
                            mView!!.cancelBookingSuccess(true)
                        },
                        {
                            NetworkUtil.handleError(it)
                            mView!!.cancelBookingSuccess(false)
                        }
                ))
    }

    override fun finishTripDriverBook(userPostId: Int) {
        val request = DriverFinishTripRequest()
        request.user_post_id = userPostId
        addDispose(RetrofitManager.driverFinishTrip(request)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.finishScreen()
                            mView!!.finishTripSucceess(true)
                        },
                        {
                            NetworkUtil.handleError(it)
                            mView!!.finishTripSucceess(false)
                        }
                ))
    }

    override fun bookUserPost(userId: Int, driverCarId: Int, userPostId: Int, price: String, note: String) {
        val request = DriverBookUserPostRequest()
        request.userId = userId
        request.driverCarId = driverCarId
        request.userPostId = userPostId
        request.driverId = CarBookingSharePreference.getUserId()
        request.price = price
        request.note = note
        request.canBook = canBook
        addDispose(RetrofitManager.driverBookUserPost(request)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            if (it?.status == 200) {
                                mView!!.finishScreen()
                                mView!!.finishBookSuccess(true)
                            } else if (it?.status == 412 && it.data != null) {
                                canBook = 1
                                ToastUtil.show(it!!.msg!!)
                            }
                        },
                        {
                            NetworkUtil.handleError(it)
                            mView!!.finishBookSuccess(false)
                        }
                )
        )
    }

}