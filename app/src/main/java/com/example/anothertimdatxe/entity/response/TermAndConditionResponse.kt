package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class TermAndConditionResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("slug")
    var slug: String? = null
    @SerializedName("content")
    var content: String? = null
}