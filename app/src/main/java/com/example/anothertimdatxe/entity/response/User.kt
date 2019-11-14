package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null
)