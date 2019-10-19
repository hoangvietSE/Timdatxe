package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserCreatePostResponse(

	@field:SerializedName("number_seat")
	val numberSeat: Int? = null,

	@field:SerializedName("lat_to")
	val latTo: String? = null,

	@field:SerializedName("price_expected")
	val priceExpected: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("end_point")
	val endPoint: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("waypoints")
	val waypoints: Any? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("lat_from")
	val latFrom: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("start_point")
	val startPoint: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lng_to")
	val lngTo: String? = null,

	@field:SerializedName("lng_from")
	val lngFrom: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
)