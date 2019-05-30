package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class ActiveRequest(
        @SerializedName("email")
        var email: String = "",
        @SerializedName("session_token")
        var session_token: String = ""
)