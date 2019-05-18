package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class UserData {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("email")
    var email: String = ""

    @SerializedName("full_name")
    var full_name: String = ""

    @SerializedName("birthday")
    var birthday: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("phone")
    var phone: String = ""

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("provider_id")
    var provider_id: String? = null

    @SerializedName("session_token")
    var session_token: String = ""
}