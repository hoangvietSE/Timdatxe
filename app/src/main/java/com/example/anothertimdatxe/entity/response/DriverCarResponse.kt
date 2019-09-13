package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverCarResponse(

        @field:SerializedName("car_name")
        val carName: String? = null,

        @field:SerializedName("full_name")
        val fullName: String? = null,

        @field:SerializedName("seat_number")
        val seatNumber: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("car_version")
        val carVersion: Int? = null
)