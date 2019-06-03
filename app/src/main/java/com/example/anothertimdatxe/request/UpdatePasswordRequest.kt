package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class UpdatePasswordRequest(
        @SerializedName("email")
        var email: String = "",
        @SerializedName("reset_password_token")
        var token: String = "",
        @SerializedName("new_password")
        var password: String = ""
)