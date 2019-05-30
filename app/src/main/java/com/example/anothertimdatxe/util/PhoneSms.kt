package com.example.anothertimdatxe.util

object PhoneSms {
    fun formatOriginalPhoneNumber(phone: String): String {
        var phoneNumber: String = ""
        phoneNumber = phone.replace("+84", "0", false)
        return phoneNumber
    }

    fun formatPhoneNumber(phone: String): String {
        var phoneFormat: String = ""
        if (phone[0].toString() == "0") {
            phoneFormat = "+84" + phone.substring(1)
        } else {
            phoneFormat = "+84$phone"
        }
        return phoneFormat
    }
}