package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class ForgotResult {
    @SerializedName("token")
    var token: String = ""
    @SerializedName("fullName")
    var fullName: String = ""
    @SerializedName("link")
    var link: String = ""
}