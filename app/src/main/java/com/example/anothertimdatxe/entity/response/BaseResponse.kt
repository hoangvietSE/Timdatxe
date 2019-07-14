package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("msg")
    var msg: String? = null
    @SerializedName("count_books")
    var count_books: Int? = null
    @SerializedName("data")
    var data: T? = null
}