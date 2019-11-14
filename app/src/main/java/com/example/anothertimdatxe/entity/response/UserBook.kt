package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserBook(

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("number_seat")
	val numberSeat: Int? = null,

	@field:SerializedName("reason")
	val reason: Int? = null,

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("lat_to")
	val latTo: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("str_status")
	val strStatus: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("app_start_province")
	val appStartProvince: String? = null,

	@field:SerializedName("end_point")
	val endPoint: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("app_end_province")
	val appEndProvince: String? = null,

	@field:SerializedName("lat_from")
	val latFrom: String? = null,

	@field:SerializedName("book_time")
	val bookTime: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lng_to")
	val lngTo: String? = null,

	@field:SerializedName("lng_from")
	val lngFrom: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("total_price")
	val totalPrice: String? = null,

	@field:SerializedName("can_finish")
	val canFinish: Int? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("start_point")
	val startPoint: String? = null,

	@field:SerializedName("driver_post_id")
	val driverPostId: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)