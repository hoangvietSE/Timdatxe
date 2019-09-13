package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DriverListPostResponse {
    @SerializedName("id")
    @Expose
    val id: Int? = null
    @SerializedName("title")
    @Expose
    val title: String? = null
    @SerializedName("slug")
    @Expose
    val slug: String? = null
    @SerializedName("empty_seat")
    @Expose
    val empty_seat: Int? = null
    @SerializedName("start_time")
    @Expose
    val start_time: String? = null
    @SerializedName("description")
    @Expose
    val description: String? = null
    @SerializedName("lat_from")
    @Expose
    val lat_from: String? = null
    @SerializedName("lng_from")
    @Expose
    val lng_from: String? = null
    @SerializedName("lat_to")
    @Expose
    val lat_to: String? = null
    @SerializedName("lng_to")
    @Expose
    val lng_to: String? = null
    @SerializedName("start_point")
    @Expose
    val start_point: String? = null
    @SerializedName("end_point")
    @Expose
    val end_point: String? = null
    @SerializedName("waypoints")
    @Expose
    val waypoints: String? = null
    @SerializedName("app_start_province")
    @Expose
    val app_start_province: String? = null
    @SerializedName("app_end_province")
    @Expose
    val app_end_province: String? = null
    @SerializedName("distance")
    @Expose
    val distance: Float? = null
    @SerializedName("car_id")
    @Expose
    val car_id: Int? = null
    @SerializedName("driver_id")
    @Expose
    val driver_id: Int? = null
    @SerializedName("min_price")
    @Expose
    val min_price: String? = null
    @SerializedName("max_price")
    @Expose
    val max_price: String? = null
    @SerializedName("regular_price")
    @Expose
    val regular_price: String? = null
    @SerializedName("price_level_1")
    @Expose
    val price_level_1: String? = null
    @SerializedName("price_level_2")
    @Expose
    val price_level_2: String? = null
    @SerializedName("price_level_3")
    @Expose
    val price_level_3: String? = null
    @SerializedName("private_price_1")
    @Expose
    val private_price_1: String? = null
    @SerializedName("private_price_2")
    @Expose
    val private_price_2: String? = null
    @SerializedName("created_by_id")
    @Expose
    val created_by_id: Int? = null
    @SerializedName("updated_by_id")
    @Expose
    val updated_by_id: Int? = null
    @SerializedName("status")
    @Expose
    val status: Int? = null
    @SerializedName("reason")
    @Expose
    val reason: Int? = null
    @SerializedName("str_reason")
    @Expose
    val str_reason: String? = null
    @SerializedName("city_id")
    @Expose
    val city_id: Int? = null
    @SerializedName("created_at")
    @Expose
    val created_at: String? = null
    @SerializedName("updated_at")
    @Expose
    val updated_at: String? = null
    @SerializedName("str_status")
    @Expose
    val str_status: String? = null
    @SerializedName("can_book")
    @Expose
    val can_book: Int? = null
    @SerializedName("flag_edit")
    @Expose
    val flag_edit: Int? = null
    @SerializedName("flag_delete")
    @Expose
    val flag_delete: Int? = null

    fun hideBtnCancel(): Boolean {
        if (flag_delete == 1) return true
        return false
    }

    @SerializedName("high_way")
    @Expose
    val high_way: Int? = 0
    @SerializedName("type")
    @Expose
    val type: Int? = 0
}