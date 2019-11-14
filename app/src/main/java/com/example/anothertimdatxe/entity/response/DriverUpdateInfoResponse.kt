package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverUpdateInfoResponse(

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("driver_license")
	val driverLicense: String? = null,

	@field:SerializedName("card_id")
	val cardId: String? = null,

	@field:SerializedName("age")
	val age: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)