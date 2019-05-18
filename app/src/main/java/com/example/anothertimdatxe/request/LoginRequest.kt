package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("email")
    var email: String = ""
    @SerializedName("password")
    var password: String = ""
    @SerializedName("remember")
    var remember: Int = 0
}