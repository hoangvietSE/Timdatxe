package com.example.anothertimdatxe.base.network

import com.google.gson.annotations.SerializedName

class BaseResult<R> {
    @SerializedName("status")
    var status: Int = 0
    @SerializedName("msg")
    var msg: String? = null
    @SerializedName("data")
    var data: R? = null
    @SerializedName("count_books")
    var count_books: Int? = null
    @SerializedName("total_page")
    var total_page: Int? = null
}