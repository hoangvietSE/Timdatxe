package com.example.anothertimdatxe.base.network

class ApiException(var message: String?, var mThrowable: Throwable) {
    // var message: both init properties and declare primary constructor
    fun apiError(): String {
        return message.toString()
    }
}