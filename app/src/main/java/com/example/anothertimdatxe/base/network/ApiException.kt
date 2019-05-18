package com.example.anothertimdatxe.base.network

class ApiException(var message: String?) {
    // var message: both init properties and declare primary constructor
    fun apiError(): String {
        return message.toString()
    }
}