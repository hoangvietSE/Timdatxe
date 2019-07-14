package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class UserHistoryResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("book_time")
    var book_time: String? = null
    @SerializedName("start_point")
    var start_point: String? = null
    @SerializedName("end_point")
    var end_point: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
    @SerializedName("lat_from")
    var lat_from: String? = null
    @SerializedName("lng_from")
    var lng_from: String? = null
    @SerializedName("lat_to")
    var lat_to: String? = null
    @SerializedName("lng_to")
    var lng_to: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("number_seat")
    var number_seat: Int? = null
    @SerializedName("type_book")
    var type_book: Int? = null
    @SerializedName("total_price")
    var total_price: String? = null
}