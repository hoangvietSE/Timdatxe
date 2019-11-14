package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DriverProfileResponse : Serializable {

    @SerializedName("birthday")
    var birthday: String? = null

    @SerializedName("google_id")
    var googleId: Int? = null

    @SerializedName("gender")
    var gender: Int? = null

    @SerializedName("avatar_new")
    var avatarNew: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("before_license_image")
    var beforeLicenseImage: String? = null

    @SerializedName("session_token")
    var sessionToken: String? = null

    @SerializedName("flag_edit")
    var flagEdit: Int? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("flag_current_driver")
    var flagCurrentDriver: Int? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("twitter_id")
    var twitterId: String? = null

    @SerializedName("driver_license")
    var driverLicense: String? = null

    @SerializedName("remember_token")
    var rememberToken: String? = null

    @SerializedName("app_id")
    var appId: String? = null

    @SerializedName("vote")
    var vote: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("count_driver_post")
    var countDriverPost: Int? = null

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("card_id")
    var cardId: Int? = null

    @SerializedName("facebook_id")
    var facebookId: Int? = null

    @SerializedName("instagram_id")
    var instagramId: Int? = null

    @SerializedName("fullName")
    var fullName: String? = null

    @SerializedName("str_gender")
    var strGender: String? = null

    @SerializedName("zalo_id")
    var zaloId: Int? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("drivercars")
    var drivercars: List<DrivercarsItem?>? = listOf()

    @SerializedName("provider_id")
    var providerId: Int? = null

    @SerializedName("after_license_image")
    var afterLicenseImage: String? = null

    @SerializedName("age")
    var age: Int? = null

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("count_books")
    var countBooks: Int? = null
}

class DrivercarsItem : Serializable {
    @SerializedName("car_name")
    var carName: String? = null

    @SerializedName("license_plate")
    var licensePlate: String? = null

    @SerializedName("str_status")
    var strStatus: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("car_id")
    var carId: Int? = null

    @SerializedName("status")
    var status: Int? = null
}