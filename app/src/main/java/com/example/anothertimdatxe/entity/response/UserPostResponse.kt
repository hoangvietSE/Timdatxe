package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class UserPostResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("user_id")
    var user_id: Int? = null
    @SerializedName("number_seat")
    var number_seat: Int? = null
    @SerializedName("start_time")
    var start_time: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("lat_from")
    var lat_from: String? = null
    @SerializedName("lng_from")
    var lng_from: String? = null
    @SerializedName("lat_to")
    var lat_to: String? = null
    @SerializedName("lng_to")
    var lng_to: String? = null
    @SerializedName("price_expected")
    var price_expected: String? = null
    @SerializedName("start_point")
    var start_point: String? = null
    @SerializedName("end_point")
    var end_point: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
}