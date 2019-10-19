package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserCancelUserBookResponse(

	@field:SerializedName("notification_type")
	val notificationType: Int? = null,

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("sound")
	val sound: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("driver_post_id")
	val driverPostId: Int? = null,

	@field:SerializedName("user_book_id")
	val userBookId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)