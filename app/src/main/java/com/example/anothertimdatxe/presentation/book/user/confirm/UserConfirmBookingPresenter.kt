package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.request.TimeBookingRequest
import com.example.anothertimdatxe.request.UserBookingRequest

interface UserConfirmBookingPresenter : BasePresenter {
    fun fetchDataBooking(driverPostId: Int)
    fun setSeatSpinner(emptySeat: Int?)
    fun paymentBooking(userBookRequest: UserBookingRequest, timeBookingRequest: TimeBookingRequest)
    fun setTypeTrip(type: Int)
    fun setNumberSeat(seat: Int)
    fun getPrice()
    fun getPercentDistance()
    fun fetchDataPlaceById(mLocationStartingPointId: String?, mLocationEndingPointId: String?)
}