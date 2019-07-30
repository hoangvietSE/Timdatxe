package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class RefreshTokenResponse {
    @SerializedName("token")
    var token: String? = ""
}