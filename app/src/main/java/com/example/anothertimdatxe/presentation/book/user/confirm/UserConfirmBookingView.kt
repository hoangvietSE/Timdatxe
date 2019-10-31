package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.map.entity.Route

interface UserConfirmBookingView : BaseView {
    fun showDataBooking(data: ConfirmBookingResponse)
    fun addSeatToSpinner(data: MutableList<String>)
    fun onNameEmpty()
    fun onNameError()
    fun onPhoneEmpty()
    fun onPhoneError()
    fun onEmailError()
    fun onEmailEmpty()
    fun onStartingPointEmpty()
    fun onEndingPointEmpty()
    fun onDateEmpty()
    fun onDateInPast()
    fun onDateBeforeStartingTime()
    fun onDateAfterEndingTime()
    fun onHourEmpty()
    fun onHourBeforeStartingTime()
    fun onHourAfterEndingTime()
    fun showPriceConvenient(oneSeatPrice: Int, totalPrice: Int)
    fun showPricePrivate(totalPrice: Int)
    fun showPercentDistance(percentage: Double)
    fun routeSuccess(route: Route)
    fun routeFail()
    fun showNumberSeat(numberSeat: Int)
}