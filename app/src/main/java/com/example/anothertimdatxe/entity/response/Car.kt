package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class Car(

	@field:SerializedName("seat_number")
	val seatNumber: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("car_id")
	val carId: Int? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null


)