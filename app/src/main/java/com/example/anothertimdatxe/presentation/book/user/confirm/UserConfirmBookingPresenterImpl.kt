package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.*
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidEmailTwo
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.map.response.GoogleMapDirectionResponse
import com.example.anothertimdatxe.request.TimeBookingRequest
import com.example.anothertimdatxe.request.UserBookingRequest
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import java.util.*

class UserConfirmBookingPresenterImpl(mView: UserConfirmBookingView) : BasePresenterImpl<UserConfirmBookingView>(mView), UserConfirmBookingPresenter {
    private var mUserBookingRequest: UserBookingRequest? = null
    private var currentCalendar = Calendar.getInstance()
    private var mUserConfirmBookingResponse: ConfirmBookingResponse? = null
    private var typeTrip: Int? = -1
    private var distance: Double = 0.0
    private var currentPrice: Int = 0
    private var numberSeat: Int = -1
    private var percentage: Double = 0.0

    override fun fetchDataBooking(driverPostId: Int) {
        mView!!.showLoading()
        addDispose(RetrofitManager.getUserConfirmBooking(driverPostId, object : ICallBack<BaseResult<ConfirmBookingResponse>> {
            override fun onSuccess(result: BaseResult<ConfirmBookingResponse>?) {
                mUserConfirmBookingResponse = result?.data
                typeTrip = mUserConfirmBookingResponse?.type
                distance = mUserConfirmBookingResponse?.distance!!
                currentPrice = getCurrentPrice()
                numberSeat = mUserConfirmBookingResponse?.emptySeat!!
                mView!!.showDataBooking(mUserConfirmBookingResponse!!)
                mView!!.hideLoading()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
            }

        }))
    }

    override fun setSeatSpinner(emptySeat: Int?) {
        val data = mutableListOf<String>()
        for (i in 1..emptySeat!!) {
            data.add(i.toString())
        }
        mView!!.addSeatToSpinner(data)
    }

    override fun paymentBooking(userBookRequest: UserBookingRequest, timeBookingRequest: TimeBookingRequest) {
        mUserBookingRequest = userBookRequest
        mUserBookingRequest?.distance = mUserConfirmBookingResponse?.distance
        mUserBookingRequest?.driverId = mUserConfirmBookingResponse?.driverId
        mUserBookingRequest?.driverPostId = mUserConfirmBookingResponse?.id
        mUserBookingRequest?.emptySeat = mUserConfirmBookingResponse?.emptySeat
        mUserBookingRequest?.latFrom = mUserConfirmBookingResponse?.latFrom
        mUserBookingRequest?.lngFrom = mUserConfirmBookingResponse?.lngFrom
        mUserBookingRequest?.latTo = mUserConfirmBookingResponse?.latTo
        mUserBookingRequest?.lngTo = mUserConfirmBookingResponse?.lngTo
        mUserBookingRequest?.note = mUserConfirmBookingResponse?.description ?: ""
        mUserBookingRequest?.type = mUserConfirmBookingResponse?.type
//        if (mUserConfirmBookingResponse?.type == Constant.CONVENIENT_TRIP) {
//            mUserBookingRequest?.price = mUserConfirmBookingResponse?.regularPrice
//            mUserBookingRequest?.totalPrice = mUserConfirmBookingResponse?.regularPrice?.toInt()?.times(edt_number_seat.text.toString().toInt())?.toString()
//        } else {
//            mUserBookingRequest?.totalPrice = mUserConfirmBookingResponse?.privatePrice2
//        }
        timeBookingRequest.startTime = mUserConfirmBookingResponse?.startTime
        timeBookingRequest.endTime = mUserConfirmBookingResponse?.endTime
        if (mUserBookingRequest?.fullName.isNullOrEmpty()) {
            mView!!.onNameEmpty()
            return
        }
        if (NumberUtil.isNumberString(mUserBookingRequest?.fullName!!)) {
            mView!!.onNameError()
            return
        }
        if (mUserBookingRequest?.phone.isNullOrEmpty()) {
            mView!!.onPhoneEmpty()
            return
        }
        if (!mUserBookingRequest?.phone!!.isValidPhone()) {
            mView!!.onPhoneError()
            return
        }
        if (mUserBookingRequest?.email.isNullOrEmpty()) {
            mView!!.onEmailEmpty()
            return
        }
        if (!mUserBookingRequest?.email!!.isValidEmail() && !mUserBookingRequest?.email!!.isValidEmailTwo()) {
            mView!!.onEmailError()
            return
        }
        validateTimeRequest(timeBookingRequest)
    }

    private fun validateTimeRequest(timeBookingRequest: TimeBookingRequest) {
        currentCalendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1)
        var dateCalendar = DateUtil.getCalendarByStamp(timeBookingRequest.bookDate!!, DateUtil.DATE_FORMAT_23)
        var startingDateCalendar = DateUtil.getCalendarByStamp(DateUtil.formatDate(
                timeBookingRequest.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23), DateUtil.DATE_FORMAT_23)
        var endingDateCalendar = DateUtil.getCalendarByStamp(DateUtil.formatDate(
                timeBookingRequest.endTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23
        ), DateUtil.DATE_FORMAT_23)
        if (timeBookingRequest.bookDate.isNullOrEmpty()) {
            mView!!.onDateEmpty()
            return
        }
        if (dateCalendar.before(currentCalendar)) {
            mView!!.onDateInPast()
            return
        }
        if (dateCalendar.before(startingDateCalendar)) {
            mView!!.onDateBeforeStartingTime()
            return
        }
        if (dateCalendar.after(endingDateCalendar)) {
            mView!!.onDateAfterEndingTime()
            return
        }
        dateCalendar = DateUtil.getCalendarByStamp("${timeBookingRequest.bookDate} ${timeBookingRequest.bookHour}", DateUtil.DATE_FORMAT_25)
        startingDateCalendar = DateUtil.getCalendarByStamp(DateUtil.formatDate(
                timeBookingRequest.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_25), DateUtil.DATE_FORMAT_25)
        endingDateCalendar = DateUtil.getCalendarByStamp(DateUtil.formatDate(
                timeBookingRequest.endTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_25
        ), DateUtil.DATE_FORMAT_25)
        if (timeBookingRequest.bookHour.isNullOrEmpty()) {
            mView!!.onHourEmpty()
            return
        }
        if (dateCalendar.before(startingDateCalendar)) {
            mView!!.onHourBeforeStartingTime()
            return
        }
        if (dateCalendar.after(endingDateCalendar)) {
            mView!!.onHourAfterEndingTime()
            return
        }
    }

    override fun setTypeTrip(type: Int) {
        typeTrip = type
    }

    override fun setNumberSeat(seat: Int) {
        numberSeat = seat
        getPrice()
    }

    override fun getPrice() {
        if (typeTrip == Constant.PRIVATE_TRIP) {
            if (percentage < 0.5) {
                currentPrice = mUserConfirmBookingResponse?.privatePrice1?.toInt()!!
            } else {
                currentPrice = mUserConfirmBookingResponse?.privatePrice2?.toInt()!!
            }
            numberSeat = mUserConfirmBookingResponse?.emptySeat!!
            mView!!.showNumberSeat(numberSeat)
            mView!!.showPricePrivate(currentPrice)
        } else {
            if (percentage < 0.3) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel1?.toInt()!!
            } else if (percentage >= 0.3 && percentage < 0.5) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel2?.toInt()!!
            } else if (percentage >= 0.5 && percentage < 0.7) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel3?.toInt()!!
            } else {
                currentPrice = mUserConfirmBookingResponse?.regularPrice?.toInt()!!
            }
            mView!!.showPriceConvenient(currentPrice, currentPrice * numberSeat)
        }
    }

    private fun getCurrentPrice(): Int {
        percentage = distance / mUserConfirmBookingResponse?.distance!!
        if (typeTrip == Constant.PRIVATE_TRIP) {
            if (percentage < 0.5) {
                currentPrice = mUserConfirmBookingResponse?.privatePrice1?.toInt()!!
            } else {
                currentPrice = mUserConfirmBookingResponse?.privatePrice2?.toInt()!!
            }
        } else {
            if (percentage < 0.3) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel1?.toInt()!!
            } else if (percentage >= 0.3 && percentage < 0.5) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel2?.toInt()!!
            } else if (percentage >= 0.5 && percentage < 0.7) {
                currentPrice = mUserConfirmBookingResponse?.priceLevel3?.toInt()!!
            } else {
                currentPrice = mUserConfirmBookingResponse?.regularPrice?.toInt()!!
            }
        }
        return currentPrice
    }

    override fun getPercentDistance() {
        mView!!.showPercentDistance(percentage)
    }

    override fun fetchDataPlaceById(mLocationStartingPointId: String?, mLocationEndingPointId: String?) {
        mView!!.showLoading()
        addDispose(MapRetrofitManager.fetchWayPoints(object : ICallBack<GoogleMapDirectionResponse> {
            override fun onSuccess(result: GoogleMapDirectionResponse?) {
                mView!!.hideLoading()
                result?.routes?.let {
                    if (it.size > 0) {
                        mView!!.routeSuccess(Route(
                                it.get(0)?.legs!![0]?.startLocation?.lat!!,
                                it.get(0)?.legs!![0]?.startLocation?.lng!!,
                                it.get(0)?.legs!![0]?.endLocation?.lat!!,
                                it.get(0)?.legs!![0]?.endLocation?.lng!!,
                                it.get(0)?.overviewPolyline?.points!!,
                                it.get(0)?.legs!![0]?.steps!!,
                                it.get(0)?.legs!![0]?.distance?.text!!,
                                it.get(0)?.legs!![0]?.duration?.value!!
                        ))
                    } else {
                        mView!!.routeFail()
                    }
                } ?: mView!!.routeFail()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                mView!!.routeFail()
            }
        }, "place_id:$mLocationStartingPointId", "place_id:$mLocationEndingPointId"))
    }
}