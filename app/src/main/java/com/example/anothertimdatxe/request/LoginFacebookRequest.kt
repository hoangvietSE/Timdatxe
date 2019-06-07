package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class LoginFacebookRequest {
    @SerializedName("social_id")
    var social_id: String = ""
    @SerializedName("social_type")
    var social_type: String = ""
    @SerializedName("device")
    var device: String = ""
    @SerializedName("device_token")
    var device_token: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("full_name")
    var full_name: String = ""
}