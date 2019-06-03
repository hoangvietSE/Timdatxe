package com.example.anothertimdatxe.util

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.application.CarBookingApplication

object ToastUtil {
    fun show(msg: String) {
        Toast.makeText(CarBookingApplication.instance, msg, LENGTH_LONG).show()
    }
}