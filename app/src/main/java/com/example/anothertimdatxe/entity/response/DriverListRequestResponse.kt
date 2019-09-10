package com.example.anothertimdatxe.entity.response

import com.example.anothertimdatxe.entity.UserData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DriverListRequestResponse {
    @SerializedName("start_point")
    var startPoint: String? = ""
    @SerializedName("end_point")
    var endPoint: String? = ""
    @SerializedName("price")
    val price: String? = "0"
    @SerializedName("user_post_id")
    val user_post_id: Int? = 0

    @SerializedName("str_status")
    val strStatus: String? = ""
    @SerializedName("status")
    val status: Int? = null
    @SerializedName("note")
    val note: String? = ""
    @SerializedName("created_date")
    val createDate: String? = ""
    @SerializedName("driver")
    val driver: DriverProfileResponse? = null
    @SerializedName("user_post")
    val userPost: UserPost? = null
    @SerializedName("user")
    val user: UserData? = null
    @SerializedName("reason")
    @Expose
    val reason: Int? = null
    @SerializedName("str_reason")
    @Expose
    val str_reason: String? = null

    class UserPost {
        val id: Int? = 0
        @SerializedName("number_seat")
        val numberSeat: Int? = 0
        @SerializedName("distance")
        val distance: Double? = 0.0
        @SerializedName("price_expected")
        val priceExpected: String? = "0"
        val title: String? = ""
        val description: String? = ""
        @SerializedName("start_time")
        val startTime: String? = ""
        @SerializedName("lat_from")
        val lat_from: String? = ""
        @SerializedName("lng_from")
        val lng_from: String? = ""
        @SerializedName("lat_to")
        val lat_to: String? = ""
        @SerializedName("lng_to")
        val lng_to: String? = ""
        @SerializedName("app_start_province")
        val app_start_province: String? = ""
        @SerializedName("app_end_province")
        val app_end_province: String? = ""
    }
}