package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class DriverProfileResponse(

        @field:SerializedName("birthday")
        val birthday: String? = null,

        @field:SerializedName("google_id")
        val googleId: Any? = null,

        @field:SerializedName("gender")
        val gender: Int? = null,

        @field:SerializedName("avatar_new")
        val avatarNew: Any? = null,

        @field:SerializedName("description")
        val description: Any? = null,

        @field:SerializedName("created_at")
        val createdAt: Any? = null,

        @field:SerializedName("before_license_image")
        val beforeLicenseImage: String? = null,

        @field:SerializedName("session_token")
        val sessionToken: String? = null,

        @field:SerializedName("flag_edit")
        val flagEdit: Int? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("provider")
        val provider: Any? = null,

        @field:SerializedName("flag_current_driver")
        val flagCurrentDriver: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("twitter_id")
        val twitterId: Any? = null,

        @field:SerializedName("driver_license")
        val driverLicense: String? = null,

        @field:SerializedName("remember_token")
        val rememberToken: String? = null,

        @field:SerializedName("app_id")
        val appId: String? = null,

        @field:SerializedName("vote")
        val vote: String? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("address")
        val address: String? = null,

        @field:SerializedName("count_driver_post")
        val countDriverPost: Int? = null,

        @field:SerializedName("avatar")
        val avatar: String? = null,

        @field:SerializedName("card_id")
        val cardId: String? = null,

        @field:SerializedName("facebook_id")
        val facebookId: Any? = null,

        @field:SerializedName("instagram_id")
        val instagramId: Any? = null,

        @field:SerializedName("full_name")
        val fullName: String? = null,

        @field:SerializedName("str_gender")
        val strGender: String? = null,

        @field:SerializedName("zalo_id")
        val zaloId: Any? = null,

        @field:SerializedName("phone")
        val phone: String? = null,

        @field:SerializedName("drivercars")
        val drivercars: List<DrivercarsItem?>? = null,

        @field:SerializedName("provider_id")
        val providerId: Any? = null,

        @field:SerializedName("after_license_image")
        val afterLicenseImage: String? = null,

        @field:SerializedName("age")
        val age: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("count_books")
        val countBooks: Int? = null
)

data class DrivercarsItem(

        @field:SerializedName("car_name")
        val carName: String? = null,

        @field:SerializedName("license_plate")
        val licensePlate: String? = null,

        @field:SerializedName("str_status")
        val strStatus: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("car_id")
        val carId: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null
)