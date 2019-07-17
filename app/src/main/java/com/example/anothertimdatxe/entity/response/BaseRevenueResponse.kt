package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class BaseRevenueResponse<T> {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("total_number_of_trips")
    var total_number_of_trips: Int? = null
    @SerializedName("total_booking_numbers")
    var total_booking_numbers: Int? = null
    @SerializedName("total_money")
    var total_money: Int? = null
    @SerializedName("data")
    var data: T? = null
}