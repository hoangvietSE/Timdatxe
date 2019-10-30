package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidEmailTwo
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.request.TimeBookingRequest
import com.example.anothertimdatxe.request.UserBookingRequest
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import java.util.*

class UserConfirmBookingPresenterImpl(mView: UserConfirmBookingView) : BasePresenterImpl<UserConfirmBookingView>(mView), UserConfirmBookingPresenter {
    private var currentCalendar = Calendar.getInstance()
    override fun fetchDataBooking(driverPostId: Int) {
        mView!!.showLoading()
        addDispose(RetrofitManager.getUserConfirmBooking(driverPostId, object : ICallBack<BaseResult<ConfirmBookingResponse>> {
            override fun onSuccess(result: BaseResult<ConfirmBookingResponse>?) {
                mView!!.showDataBooking(result?.data!!)
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
            data?.add(i.toString())
        }
        mView!!.addSeatToSpinner(data)
    }

    override fun paymentBooking(userBookRequest: UserBookingRequest, timeBookingRequest: TimeBookingRequest) {
        if (userBookRequest.fullName.isNullOrEmpty()) {
            mView!!.onNameEmpty()
            return
        }
        if (NumberUtil.isNumberString(userBookRequest.fullName!!)) {
            mView!!.onNameError()
            return
        }
        if (userBookRequest.phone.isNullOrEmpty()) {
            mView!!.onPhoneEmpty()
            return
        }
        if (!userBookRequest.phone!!.isValidPhone()) {
            mView!!.onPhoneError()
            return
        }
        if (userBookRequest.email.isNullOrEmpty()) {
            mView!!.onEmailEmpty()
            return
        }
        if (!userBookRequest.email!!.isValidEmail() && !userBookRequest.email!!.isValidEmailTwo()) {
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
}