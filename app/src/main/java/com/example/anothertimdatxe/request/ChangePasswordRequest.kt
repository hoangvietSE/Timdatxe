package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class ChangePasswordRequest {
    @SerializedName("password")
    var password: String = ""

    @SerializedName("new_password")
    var new_password: String = ""
}