package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DriverPostResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("empty_seat")
    var empty_seat: Int? = null
    @SerializedName("start_time")
    var start_time: String? = null
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
    @SerializedName("start_point")
    var start_point: String? = null
    @SerializedName("end_point")
    var end_point: String? = null
    @SerializedName("full_name")
    var full_name: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
    @SerializedName("regular_price")
    @Expose
    val regularPrice: String? = ""
    @SerializedName("price_level_1")
    @Expose
    val priceLevel1: String? = ""
    @SerializedName("price_level_2")
    @Expose
    val priceLevel2: String? = ""
    @SerializedName("price_level_3")
    @Expose
    val priceLevel3: String? = ""
    @SerializedName("private_price_1")
    @Expose
    var private_price_1: String? = null
    @SerializedName("private_price_2")
    @Expose
    var private_price_2: String? = null
}