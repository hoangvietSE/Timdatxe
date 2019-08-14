package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserReviewDriverResponse(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("driver_posst_id")
	val driverPosstId: Any? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("driver_post_id")
	val driverPostId: Any? = null,

	@field:SerializedName("driver_book_id")
	val driverBookId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote")
	val vote: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)