package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class PostDetailResponse(

        @field:SerializedName("number_seat")
        val numberSeat: Int? = null,

        @field:SerializedName("reason")
        val reason: Int? = null,

        @field:SerializedName("can_refresh")
        val canRefresh: Int? = null,

        @field:SerializedName("lat_to")
        val latTo: String? = null,

        @field:SerializedName("price_expected")
        val priceExpected: Any? = null,

        @field:SerializedName("str_status")
        val strStatus: String? = null,

        @field:SerializedName("distance")
        val distance: Double? = null,

        @field:SerializedName("app_start_province")
        val appStartProvince: String? = null,

        @field:SerializedName("end_point")
        val endPoint: String? = null,

        @field:SerializedName("user_cancel_booking")
        val userCancelBooking: Int? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("waypoints")
        val waypoints: String? = null,

        @field:SerializedName("app_end_province")
        val appEndProvince: String? = null,

        @field:SerializedName("lat_from")
        val latFrom: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("can_book")
        val canBook: Int? = null,

        @field:SerializedName("edited_at")
        val editedAt: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("lng_to")
        val lngTo: String? = null,

        @field:SerializedName("slug")
        val slug: Any? = null,

        @field:SerializedName("lng_from")
        val lngFrom: String? = null,

        @field:SerializedName("user_cancel_driverbook")
        val userCancelDriverbook: Any? = null,

        @field:SerializedName("can_edit")
        val canEdit: Int? = null,

        @field:SerializedName("can_finish")
        val canFinish: Int? = null,

        @field:SerializedName("start_time")
        val startTime: String? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("start_point")
        val startPoint: String? = null,

        @field:SerializedName("duration_time")
        val durationTime: Double? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("city_id")
        val cityId: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("str_reason")
        val strReason: String? = null
)