package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserRequestDetailResponse(

	@field:SerializedName("driver_post")
	val driverPost: DriverPost? = null,

	@field:SerializedName("driver")
	val driver: Driver? = null,

	@field:SerializedName("can_cancel_booking")
	val canCancelBooking: Int? = null,

	@field:SerializedName("cancel_booking")
	val cancelBooking: CancelBooking? = null,

	@field:SerializedName("user_book")
	val userBook: UserBook? = null,

	@field:SerializedName("driver_car")
	val driverCar: DriverCarDetail? = null
)