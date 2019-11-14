package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class RegisterRequest(
        @SerializedName("fullName")
        var fullName: String? = null,
        @SerializedName("email")
        var email: String? = null,
        @SerializedName("password")
        var password: String? = null,
        @SerializedName("phone")
        var phone: String? = null
)