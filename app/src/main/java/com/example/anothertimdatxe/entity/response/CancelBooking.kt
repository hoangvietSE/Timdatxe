package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class CancelBooking(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("current_date")
	val currentDate: String? = null,

	@field:SerializedName("refund")
	val refund: String? = null
)