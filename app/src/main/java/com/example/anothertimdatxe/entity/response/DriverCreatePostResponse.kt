package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverCreatePostResponse(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("lat_to")
	val latTo: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("end_point")
	val endPoint: Any? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("waypoints")
	val waypoints: String? = null,

	@field:SerializedName("regular_price")
	val regularPrice: String? = null,

	@field:SerializedName("lat_from")
	val latFrom: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price_level_3")
	val priceLevel3: String? = null,

	@field:SerializedName("private_price_2")
	val privatePrice2: String? = null,

	@field:SerializedName("price_level_1")
	val priceLevel1: String? = null,

	@field:SerializedName("price_level_2")
	val priceLevel2: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lng_to")
	val lngTo: String? = null,

	@field:SerializedName("car_id")
	val carId: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("lng_from")
	val lngFrom: String? = null,

	@field:SerializedName("private_price_1")
	val privatePrice1: String? = null,

	@field:SerializedName("empty_seat")
	val emptySeat: String? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("schedule")
	val schedule: String? = null,

	@field:SerializedName("max_price")
	val maxPrice: String? = null,

	@field:SerializedName("min_price")
	val minPrice: String? = null,

	@field:SerializedName("start_point")
	val startPoint: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
)