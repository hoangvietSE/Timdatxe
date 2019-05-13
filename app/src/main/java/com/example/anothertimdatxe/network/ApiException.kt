package com.example.anothertimdatxe.network

import android.util.Log

class ApiException(var message: String = "") {
    // var message: both init properties and declare primary constructor
    fun ApiError() {
        Log.d("myLog", message)
    }
}