package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class VersionAppResponse {
    @SerializedName("app_version")
    var app_version: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("release_date")
    var release_date: String? = null
}