package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

data class BannerHomeResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)