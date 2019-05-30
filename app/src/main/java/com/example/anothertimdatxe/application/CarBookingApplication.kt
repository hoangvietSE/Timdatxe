package com.example.anothertimdatxe.application

import android.app.Application

class CarBookingApplication : Application() {

    companion object {
        lateinit var instance: CarBookingApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}