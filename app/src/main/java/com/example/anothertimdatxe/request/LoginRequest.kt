package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("email")
    var email:String?=null
    @SerializedName("password")
    var password:String?=null
    @SerializedName("remember")
    var remember:Int?=0
}