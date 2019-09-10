package com.example.anothertimdatxe.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DriverCreatePostRequest {
    @SerializedName("session_token")
    @Expose()
    val session_token: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("empty_seat")
    @Expose
    var empty_seat: Int? = null
    @SerializedName("regular_price")
    @Expose
    var regular_price: String? = null
    @SerializedName("price_level_1")
    @Expose
    var price_level_1: String? = null
    @SerializedName("price_level_2")
    @Expose
    var price_level_2: String? = null
    @SerializedName("price_level_3")
    @Expose
    var price_level_3: String? = null
    @SerializedName("start_time")
    @Expose
    var start_time: String? = null
    @SerializedName("car_id")
    @Expose
    var car_id: String? = null
    @SerializedName("min_price")
    @Expose
    var min_price: String? = null
    @SerializedName("max_price")
    @Expose
    var max_price: String? = null
    @SerializedName("lat_from")
    @Expose
    var lat_from: String? = null
    @SerializedName("lng_from")
    @Expose
    var lng_from: String? = null
    @SerializedName("lat_to")
    @Expose
    var lat_to: String? = null
    @SerializedName("lng_to")
    @Expose
    var lng_to: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("schedule")
    @Expose
    var schedule: String? = null
    @SerializedName("type")
    @Expose
    var type: Int = 0
    @SerializedName("private_price_1")
    @Expose
    var private_price_1: String? = null
    @SerializedName("private_price_2")
    @Expose
    var private_price_2: String? = null
    @SerializedName("waypoints")
    @Expose
    var waypoints: String? = null
    @SerializedName("distance")
    @Expose
    var distance: Double? = null
    @SerializedName("start_point")
    @Expose
    var start_point: String? = null
    @SerializedName("end_point")
    @Expose
    var end_point: String? = null
    @SerializedName("duration_time")
    @Expose
    var duration_time: Double? = null
    @SerializedName("high_way")
    @Expose
    var high_way: Int? = 0
    var date: String? = null
    var time: String? = null
}