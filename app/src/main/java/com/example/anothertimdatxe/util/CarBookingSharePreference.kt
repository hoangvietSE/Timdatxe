package com.example.anothertimdatxe.util

import android.preference.PreferenceManager
import com.example.anothertimdatxe.application.CarBookingApplication
import com.example.anothertimdatxe.entity.UserData
import com.google.gson.Gson

object CarBookingSharePreference {
    private const val USER_DATA = "user_data"
    private var mUserId: Int? = -1

    fun setStringPreference(key: String?, data: String?) {
        var preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        var editor = preference.edit()
        editor.putString(key, data)
        editor.apply()
    }

    fun getStringPreference(key: String): String {
        var preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        return preference.getString(key, "")
    }

    fun setUserData(userData: UserData) {
        var json = Gson().toJson(userData)
        setStringPreference(USER_DATA, json)
    }

    fun getUserData(): UserData? {
        var json = getStringPreference(USER_DATA)
        return Gson().fromJson(json, UserData::class.java)
    }

    fun getUserId(): Int {
        if (mUserId == null) {
            getUserData()?.let {
                mUserId = it.id
            }
        }
        return mUserId ?: -1
    }

    fun clearAllPreference() {
        var preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        var editor = preference.edit()
        mUserId = null
        editor.clear()
        editor.apply()
    }

}