package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class ForgotResult {
    @SerializedName("token")
    var token: String = ""
    @SerializedName("full_name")
    var full_name: String = ""
    @SerializedName("link")
    var link: String = ""
}