package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class Driver(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null
)