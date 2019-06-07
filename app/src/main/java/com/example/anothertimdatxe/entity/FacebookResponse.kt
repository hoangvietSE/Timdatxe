package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class FacebookResponse {
    @SerializedName("name")
    var name: String = ""
    @SerializedName("email")
    var email: String = ""
}