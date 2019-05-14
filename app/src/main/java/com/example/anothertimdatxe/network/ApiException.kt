package com.example.anothertimdatxe.network

import android.util.Log

class ApiException(var msg: String = "") {
    // var message: both init properties and declare primary constructor
    fun apiError() {
        Log.d("myLogError", msg)
    }
}