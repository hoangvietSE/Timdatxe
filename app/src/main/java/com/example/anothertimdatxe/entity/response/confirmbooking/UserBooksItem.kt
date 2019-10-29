package com.example.anothertimdatxe.entity.response.confirmbooking

import com.google.gson.annotations.SerializedName

data class UserBooksItem(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("total_number_seat")
	val totalNumberSeat: String? = null
)