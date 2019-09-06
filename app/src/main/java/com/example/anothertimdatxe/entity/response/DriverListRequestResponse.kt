package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverListRequestResponse(

        @field:SerializedName("note")
        val note: Any? = null,

        @field:SerializedName("driver_id")
        val driverId: Int? = null,

        @field:SerializedName("str_status")
        val strStatus: String? = null,

        @field:SerializedName("end_point")
        val endPoint: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("driver")
        val driver: Driver? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("price")
        val price: String? = null,

        @field:SerializedName("start_point")
        val startPoint: String? = null,

        @field:SerializedName("user_post_id")
        val userPostId: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("driver_car_id")
        val driverCarId: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("avatar")
        val avatar: String? = null

)