package com.example.anothertimdatxe.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class PaymentRequest() : Parcelable {
    @SerializedName("amount")
    var totalPrice: Int = 0
    @SerializedName("payment_type")
    var paymentMethod: String = ""
    @SerializedName("transaction")
    var transaction: String = ""
    @SerializedName("device_id")
    var deviceId: String = ""
    @SerializedName("user_book_id")
    var user_book_id: Int = 0
    @SerializedName("driver_book_id")
    var driver_book_id: Int = 0
    @SerializedName("trans_ref_no")
    var trans_ref_no: String = ""
    @SerializedName("reference_number")
    var reference_number: String = ""
    @SerializedName("message")
    var message: String = ""
    @SerializedName("status")
    var status: Int = 0
    @SerializedName("sign")
    var sign: String = ""

    constructor(parcel: Parcel) : this() {
        totalPrice = parcel.readInt()
        paymentMethod = parcel.readString()
        transaction = parcel.readString()
        deviceId = parcel.readString()
        user_book_id = parcel.readInt()
        trans_ref_no = parcel.readString()
        reference_number = parcel.readString()
        message = parcel.readString()
        status = parcel.readInt()
        sign = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(totalPrice)
        parcel.writeString(paymentMethod)
        parcel.writeString(transaction)
        parcel.writeString(deviceId)
        parcel.writeInt(user_book_id)
        parcel.writeString(trans_ref_no)
        parcel.writeString(reference_number)
        parcel.writeString(message)
        parcel.writeInt(status)
        parcel.writeString(sign)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentRequest> {
        override fun createFromParcel(parcel: Parcel): PaymentRequest {
            return PaymentRequest(parcel)
        }

        override fun newArray(size: Int): Array<PaymentRequest?> {
            return arrayOfNulls(size)
        }
    }


}