package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class DriverDataResponse {
    val id: Int? = 0
    var birthday: String? = ""
    var address: String? = ""
    var gender: Int = 0
    var email: String? = ""
    var phone: String? = ""
    var description: String? = ""
    @SerializedName("full_name")
    var fullName: String? = ""
    @SerializedName("avatar")
    var avatar: String = ""
    @SerializedName("avatar_new")
    var avatarNew: String = ""
    var before_license_image: String? = null
    var after_license_image: String? = null
}