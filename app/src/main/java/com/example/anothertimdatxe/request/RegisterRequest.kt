package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class RegisterRequest(
        @SerializedName("full_name")
        var full_name: String? = null,
        @SerializedName("email")
        var email: String? = null,
        @SerializedName("password")
        var password: String? = null,
        @SerializedName("phone")
        var phone: String? = null
)