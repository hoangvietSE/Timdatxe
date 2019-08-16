package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class DriverCarBrandDetailResponse {
    @SerializedName("id")
    var id: Int = -1
    @SerializedName("name")
    var name: String = ""
    @SerializedName("car_brand_id")
    var car_brand_id: Int = -1
}