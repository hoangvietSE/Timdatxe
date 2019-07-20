package com.example.anothertimdatxe.request

import com.google.gson.annotations.SerializedName

class ContactRequest {
    @SerializedName("title")
    var title: String? = null
    @SerializedName("message")
    var message: String? = null
}