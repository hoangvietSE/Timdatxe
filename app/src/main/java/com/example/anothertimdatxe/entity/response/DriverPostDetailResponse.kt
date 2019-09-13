package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverPostDetailResponse(

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

        @field:SerializedName("flag_show_list_book")
        val flagShowListBook: Int? = null,

        @field:SerializedName("description")
        val description: Any? = null,

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

        @field:SerializedName("regular_price")
        val regularPrice: Any? = null,

        @field:SerializedName("flag_edit")
        val flagEdit: Int? = null,

        @field:SerializedName("private_car")
        val privateCar: Int? = null,

        @field:SerializedName("lat_from")
        val latFrom: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("car")
        val car: Car? = null,

        @field:SerializedName("can_book")
        val canBook: Int? = null,

        @field:SerializedName("price_level_3")
        val priceLevel3: Any? = null,

        @field:SerializedName("created_seat")
        val createdSeat: Int? = null,

        @field:SerializedName("private_price_2")
        val privatePrice2: String? = null,

        @field:SerializedName("price_level_1")
        val priceLevel1: Any? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("price_level_2")
        val priceLevel2: Any? = null,

        @field:SerializedName("lng_to")
        val lngTo: String? = null,

        @field:SerializedName("flag_cancel_booking")
        val flagCancelBooking: Int? = null,

        @field:SerializedName("car_id")
        val carId: Int? = null,

        @field:SerializedName("slug")
        val slug: String? = null,

        @field:SerializedName("lng_from")
        val lngFrom: String? = null,

        @field:SerializedName("private_price_1")
        val privatePrice1: String? = null,

        @field:SerializedName("flag_delete")
        val flagDelete: Int? = null,

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
        val durationTime: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("city_id")
        val cityId: Any? = null,

        @field:SerializedName("high_way")
        val high_way: Int? = 0
)