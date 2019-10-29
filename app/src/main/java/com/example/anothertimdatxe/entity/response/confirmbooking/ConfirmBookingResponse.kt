package com.example.anothertimdatxe.entity.response.confirmbooking

import com.google.gson.annotations.SerializedName

data class ConfirmBookingResponse(

	@field:SerializedName("high_way")
	val highWay: Int? = null,

	@field:SerializedName("reason")
	val reason: Int? = null,

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("lat_to")
	val latTo: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("end_point")
	val endPoint: String? = null,

	@field:SerializedName("app_start_province")
	val appStartProvince: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("waypoints")
	val waypoints: String? = null,

	@field:SerializedName("app_end_province")
	val appEndProvince: String? = null,

	@field:SerializedName("driver_car")
	val driverCar: DriverCar? = null,

	@field:SerializedName("regular_price")
	val regularPrice: String? = null,

	@field:SerializedName("private_car")
	val privateCar: Int? = null,

	@field:SerializedName("lat_from")
	val latFrom: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price_level_3")
	val priceLevel3: String? = null,

	@field:SerializedName("created_seat")
	val createdSeat: Int? = null,

	@field:SerializedName("private_price_2")
	val privatePrice2: String? = null,

	@field:SerializedName("price_level_1")
	val priceLevel1: String? = null,

	@field:SerializedName("edited_at")
	val editedAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("price_level_2")
	val priceLevel2: String? = null,

	@field:SerializedName("lng_to")
	val lngTo: String? = null,

	@field:SerializedName("car_id")
	val carId: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("lng_from")
	val lngFrom: String? = null,

	@field:SerializedName("private_price_1")
	val privatePrice1: String? = null,

	@field:SerializedName("end_time")
	val endTime: String? = null,

	@field:SerializedName("empty_seat")
	val emptySeat: Int? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("schedule")
	val schedule: Any? = null,

	@field:SerializedName("driver")
	val driver: Driver? = null,

	@field:SerializedName("start_point")
	val startPoint: String? = null,

	@field:SerializedName("duration_time")
	val durationTime: Double? = null,

	@field:SerializedName("userBooks")
	val userBooks: List<UserBooksItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
)