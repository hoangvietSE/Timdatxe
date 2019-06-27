package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class FaqsRequest {
    @SerializedName("limit")
    var limit: Int = 0
    @SerializedName("cat_id")
    var cat_id: Int = 0
    @SerializedName("page")
    var page: Int = 0
}