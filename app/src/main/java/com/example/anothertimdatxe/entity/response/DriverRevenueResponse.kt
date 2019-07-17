package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class DriverRevenueResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("start_point")
    var start_point: String? = null
    @SerializedName("end_point")
    var end_point: String? = null
    @SerializedName("start_time")
    var start_time: String? = null
    @SerializedName("start_date")
    var start_date: String? = null
    @SerializedName("total_money_trip")
    var total_money_trip: Int? = null
    @SerializedName("total_booking_trip")
    var total_booking_trip: Int? = null
}