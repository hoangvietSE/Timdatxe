package com.example.anothertimdatxe.entity.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class HotCitiesResponse() : Parcelable {
    @SerializedName("id")
    val id: Int? = null
    @SerializedName("name")
    val name: Int? = null
    @SerializedName("slug")
    val slug: Int? = null
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("image")
    val image: Int? = null
    @SerializedName("sort")
    val sort: Int? = null
    @SerializedName("created_at")
    val created_at: Int? = null
    @SerializedName("updated_at")
    val updated_at: Int? = null
    @SerializedName("app_image")
    val app_image: Int? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotCitiesResponse> {
        override fun createFromParcel(parcel: Parcel): HotCitiesResponse {
            return HotCitiesResponse(parcel)
        }

        override fun newArray(size: Int): Array<HotCitiesResponse?> {
            return arrayOfNulls(size)
        }
    }

}