package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class UserUpdateInfoResponse(

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("google_id")
	val googleId: Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("avatar")
	val avatar: Any? = null,

	@field:SerializedName("facebook_id")
	val facebookId: Any? = null,

	@field:SerializedName("instagram_id")
	val instagramId: Any? = null,

	@field:SerializedName("session_token")
	val sessionToken: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("zalo_id")
	val zaloId: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("provider")
	val provider: Any? = null,

	@field:SerializedName("provider_id")
	val providerId: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("twitter_id")
	val twitterId: Any? = null,

	@field:SerializedName("remember_token")
	val rememberToken: Any? = null,

	@field:SerializedName("app_id")
	val appId: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)