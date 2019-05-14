package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class UserData {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("email")
    var email: String = " "
    @SerializedName("user_name")
    var user_name: String = " "
    @SerializedName("birthday")
    var birthday: String = " "
    @SerializedName("address")
    var address: String = " "
    @SerializedName("full_name")
    var full_name: String = " "
    @SerializedName("role_id")
    var role_id: Int = 0
    @SerializedName("role_name")
    var role_name: String = " "
    @SerializedName("role_display_name")
    var role_display_name: String = " "
    @SerializedName("phone")
    var phone: String = " "
    @SerializedName("avatar")
    var avatar: String = " "
    @SerializedName("session_token")
    var session_token: String = " "
    @SerializedName("gender")
    var gender: String = " "


}