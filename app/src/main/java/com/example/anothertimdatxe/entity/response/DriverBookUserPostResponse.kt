package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverBookUserPostResponse(

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("user_post_id")
	val userPostId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("driver_book_option_id")
	val driverBookOptionId: Int? = null,

	@field:SerializedName("avatar")
	val avatar: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)