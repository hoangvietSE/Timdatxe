package com.example.anothertimdatxe.presentation.book.user.confirm

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface UserConfirmBookingPresenter : BasePresenter {
    fun fetchDataBooking(driverPostId:Int)
    fun setSeatSpinner(emptySeat: Int?)
}