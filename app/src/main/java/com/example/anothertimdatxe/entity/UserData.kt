package com.example.anothertimdatxe.entity

import com.google.gson.annotations.SerializedName

class UserData {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("email")
    var email: String? = ""

    @SerializedName("user_name")
    var userName: String = ""

    @SerializedName("birthday")
    var birthday: String? = ""

    @SerializedName("address")
    var address: String? = ""

    @SerializedName("full_name")
    var fullName: String? = null

    @SerializedName("role_id")
    var roleId: Int = 1

    @SerializedName("role_name")
    var roleName: String = ""

    @SerializedName("role_display_name")
    var roleDisplayName: String = ""

    @SerializedName("phone")
    var phone: String? = ""

    @SerializedName("avatar")
    var avatar: String = ""

    @SerializedName("session_token")
    var session_token: String = ""

    @SerializedName("gender")
    var gender: String = ""

}