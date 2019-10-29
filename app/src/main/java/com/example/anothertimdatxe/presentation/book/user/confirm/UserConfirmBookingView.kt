package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse

interface UserConfirmBookingView : BaseView {
    fun showDataBooking(data:ConfirmBookingResponse)
    fun addSeatToSpinner(data: MutableList<String>)
}