package com.example.anothertimdatxe.entity.response

import com.google.gson.annotations.SerializedName

class FaqsResponse {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("title")
    var title: String = ""
    @SerializedName("content")
    var content: String = ""
    @SerializedName("faq_cat_id")
    var faq_cat_id: String = ""
}