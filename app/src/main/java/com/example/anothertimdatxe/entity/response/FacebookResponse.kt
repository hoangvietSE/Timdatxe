package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class FacebookResponse {
    @SerializedName("name")
    var name: String = ""
    @SerializedName("email")
    var email: String = ""
}