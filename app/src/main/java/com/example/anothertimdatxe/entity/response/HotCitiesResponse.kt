package com.example.anothertimdatxe.entity.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class HotCitiesResponse() : Parcelable {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("slug")
    var slug: String? = null
    @SerializedName("code")
    var code: String? = null
    @SerializedName("image")
    var image: String? = null
    @SerializedName("sort")
    var sort: Int? = null
    @SerializedName("created_at")
    var created_at: String? = null
    @SerializedName("updated_at")
    var updated_at: String? = null
    @SerializedName("app_image")
    var app_image: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        slug = parcel.readString()
        code = parcel.readString()
        image = parcel.readString()
        sort = parcel.readInt()
//        created_at = parcel.readString()
//        updated_at = parcel.readString()
        app_image = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id!!)
        parcel.writeString(name!!)
        parcel.writeString(slug!!)
        parcel.writeString(code!!)
        parcel.writeString(image!!)
        parcel.writeInt(sort!!)
//        parcel.writeString(created_at!!)
//        parcel.writeString(updated_at!!)
        parcel.writeString(app_image!!)
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