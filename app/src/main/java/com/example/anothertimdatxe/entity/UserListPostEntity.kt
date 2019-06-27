package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class UserListPostEntity {
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
    @SerializedName("slug")
    var slug: String? = null
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
    @SerializedName("waypoints")
    var waypoints: String? = null
    @SerializedName("distance")
    var distance: Double? = 0.0
    @SerializedName("price_expected")
    var price_expected: String? = null
    @SerializedName("start_point")
    var start_point: String? = null
    @SerializedName("end_point")
    var end_point: String? = null
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("city_id")
    var city_id: Int? = null
    @SerializedName("created_at")
    var created_at: String? = null
    @SerializedName("updated_at")
    var updated_at: String? = null
    @SerializedName("str_status")
    var str_status: String? = null
    @SerializedName("can_delete")
    var can_delete: Int? = null
}