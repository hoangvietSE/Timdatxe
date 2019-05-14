package com.example.anothertimdatxe.network

import com.google.gson.annotations.SerializedName

class BaseResult<R> {
    @SerializedName("status")
    var status: Int = 0
    @SerializedName("msg")
    var msg: String = ""
    @SerializedName("data")
    var data: R? = null
}