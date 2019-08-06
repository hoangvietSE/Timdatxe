package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class DriverBookUserPostRequest {
    @SerializedName("user_id")
    var userId: Int? = null
    @SerializedName("driver_car_id")
    var driverCarId: Int? = null
    @SerializedName("user_post_id")
    var userPostId: Int? = null
    @SerializedName("driver_id")
    var driverId: Int? = null
    @SerializedName("price")
    var price: String? = null
    @SerializedName("note")
    var note: String? = null
    @SerializedName("can_book")
    var canBook: Int? = null
}