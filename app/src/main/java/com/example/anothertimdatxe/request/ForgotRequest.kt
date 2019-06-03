package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class ForgotRequest(
        @SerializedName("email")
        var email: String = ""
)