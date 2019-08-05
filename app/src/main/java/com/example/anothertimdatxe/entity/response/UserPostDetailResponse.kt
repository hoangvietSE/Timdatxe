package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserPostDetailResponse : Serializable {
        @SerializedName("id")
        @Expose
        val id: Int? = null
        @SerializedName("number_seat")
        @Expose
        val number_seat: Int? = 0
        @SerializedName("start_time")
        @Expose
        val start_time: String? = ""
        @SerializedName("str_status")
        @Expose
        val str_status: String? = ""
        @SerializedName("str_reason")
        @Expose
        val str_reason: String? = ""
        @SerializedName("status")
        @Expose
        val status: Int? = null
        @SerializedName("reason")
        @Expose
        val reason: Int? = null
        @SerializedName("user_id")
        @Expose
        val user_id: Int? = null
        @SerializedName("title")
        @Expose
        val title: String? = ""
        @SerializedName("start_point")
        @Expose
        val start_point: String? = null

        @SerializedName("end_point")
        @Expose
        val end_point: String? = null

        @SerializedName("price_expected")
        @Expose
        val price_expected: Double? = 0.0

        @SerializedName("driver_book_option_id")
        @Expose
        val driverBookOptionID: Int? = null

        @SerializedName("driver_book_id")
        @Expose
        val driverBookId: Int? = null

        @SerializedName("lat_from")
        @Expose
        val lat_from: String? = ""
        @SerializedName("lng_from")
        @Expose
        val lng_from: String? = ""
        @SerializedName("lat_to")
        @Expose
        val lat_to: String? = ""
        @SerializedName("waypoints")
        @Expose
        val wayPoint: String? = ""
        @SerializedName("distance")
        @Expose
        val distance: Double? = 0.0
        @SerializedName("lng_to")
        @Expose
        val lng_to: String? = ""
        @SerializedName("drivers")
        val drivers: ArrayList<DriverInfo>? = ArrayList()
        @SerializedName("driverCars")
        val driverCars: ArrayList<DriverCar>? = ArrayList()
        @SerializedName("user")
        val user: UserInfo? = null
        @SerializedName("can_edit")
        val canEdit: Int? = null
        @SerializedName("can_book")
        val canBook: Int? = null
        @SerializedName("description")
        @Expose
        val description: String? = ""

        @SerializedName("user_cancel_booking")
        val canCancleBooking: Int? = null
        @SerializedName("driver_cancel_booking")
        val driverCancelBooking: Int? = null

        @SerializedName("can_finish")
        val canFinish: Int? = null

        @SerializedName("driver_book_price")
        val driverBookTotalPrice: String? = null
        @SerializedName("driver_car_pending")
        val driverCarPending: Int? = null
        @SerializedName("driver_car_msg")
        val driverCarMsg: String? = null
        @SerializedName("can_refresh")
        val canRefresh: Int? = null
        @SerializedName("str_driver_status")
        val str_driver_status: String? = ""
        @SerializedName("code")
        val code: String? = ""
        @SerializedName("duration_time")
        val duration_time : String? = ""
        @SerializedName("driver_book")
        val driver_book: DriverBook? = null
        @SerializedName("driver_can_request")
        val driver_can_request: Boolean? = null
}

class UserInfo : Serializable {
        @SerializedName("full_name")
        val fullName: String? = ""
        val avatar: String? = ""
        val address: String? = ""
        @SerializedName("birthday")
        val dob: String? = ""
        val gender: String? = ""
        @SerializedName("phone")
        val phone: String? = ""
}

class DriverInfo : Serializable {
        @SerializedName("id")
        @Expose
        val id: Int? = null
        @SerializedName("full_name")
        @Expose
        val fullName: String? = ""
        @SerializedName("email")
        @Expose
        val email: String? = ""
        @SerializedName("phone")
        @Expose
        val phone: String? = ""
        @SerializedName("birthday")
        @Expose
        val birthday: String? = ""
        @SerializedName("gender")
        @Expose
        val gender: Int? = null
        @SerializedName("age")
        @Expose
        val age: String? = ""
        @SerializedName("avatar")
        @Expose
        val avatar: String? = ""
        @SerializedName("address")
        @Expose
        val address: String? = ""
        @SerializedName("provider")
        @Expose
        val provider: String? = ""
        @SerializedName("provider_id")
        @Expose
        val providerId: String? = ""
        @SerializedName("app_id")
        @Expose
        val appId: String? = ""
        @SerializedName("facebook_id")
        @Expose
        val facebookId: String? = ""
        @SerializedName("google_id")
        @Expose
        val googleId: String? = ""
        @SerializedName("twitter_id")
        @Expose
        val twitterId: String? = ""
        @SerializedName("instagram_id")
        @Expose
        val instagramId: String? = ""
        @SerializedName("zalo_id")
        @Expose
        val zaloId: String? = ""
        @SerializedName("car_id")
        @Expose
        val cardId: Int? = null
        @SerializedName("driver_license")
        @Expose
        val driverLicense: String? = ""
        @SerializedName("before_license_image")
        @Expose
        val beforeLicenseImage: String? = ""
        @SerializedName("after_license_image")
        @Expose
        val afterLicenseImage: String? = ""
        @SerializedName("description")
        @Expose
        val description: String? = ""
        @SerializedName("vote")
        @Expose
        val vote: Double? = null
        @SerializedName("session_token")
        @Expose
        val sessionToken: String? = ""
        @SerializedName("status")
        @Expose
        val status: Int? = null
        @SerializedName("remember_token")
        @Expose
        val rememberToken: String? = ""
        @SerializedName("created_at")
        @Expose
        val createdAt: String? = ""
        @SerializedName("updated_at")
        @Expose
        val updatedAt: String? = ""
        @SerializedName("avatar_new")
        @Expose
        val avatarNew: String? = ""
        @SerializedName("price")
        @Expose
        val price: String? = ""
        @SerializedName("note")
        @Expose
        val note: String? = ""
        @SerializedName("str_gender")
        @Expose
        val strGender: String? = ""
        @SerializedName("driver_car")
        @Expose
        val driver_car: DriverCar? = null
        @SerializedName("driver_book_option_id")
        @Expose
        val driverBookOptionId: Int? = null
        @SerializedName("driver_posts_size")
        @Expose
        val driverPostsSize: Int? = null
}

class DriverCar : Serializable {
        @SerializedName("id")
        @Expose
        val id: Int? = null
        @SerializedName("car_id")
        @Expose
        val carId: Int? = null
        @SerializedName("car_name")
        @Expose
        val carName: String? = ""
        @SerializedName("full_name")
        @Expose
        val fullName: String? = ""
        @SerializedName("driver_id")
        @Expose
        val driverId: Int? = null
        @SerializedName("color")
        @Expose
        val color: String? = ""
        @SerializedName("license_plate")
        @Expose
        val licensePlate: String? = ""
        @SerializedName("img1")
        @Expose
        val img1: String? = ""
        @SerializedName("img2")
        @Expose
        val img2: String? = ""
        @SerializedName("img3")
        @Expose
        val img3: String? = ""
        @SerializedName("img4")
        @Expose
        val img4: String? = ""
        @SerializedName("img5")
        @Expose
        val img5: String? = ""
        @SerializedName("note")
        @Expose
        val note: String? = ""
        @SerializedName("rank")
        @Expose
        val rank: String? = ""
        @SerializedName("registration_date")
        @Expose
        val registrationDate: String? = ""
        @SerializedName("status")
        @Expose
        val status: Int? = null
}

class DriverBook : Serializable {
        @SerializedName("driver_book_option_id")
        val driver_book_option_id: Int? = null
        @SerializedName("status")
        val status: Int? = null
        @SerializedName("str_status")
        val str_status: String? = null
        @SerializedName("full_name")
        val full_name: String? = null
        @SerializedName("str_reason")
        @Expose
        val str_reason: String? = ""
        @SerializedName("price")
        @Expose
        val price: String? = ""
        @SerializedName("driver_car_id")
        @Expose
        val driver_car_id: Int? = null
}
