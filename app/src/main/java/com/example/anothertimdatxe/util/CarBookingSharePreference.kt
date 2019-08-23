package com.example.anothertimdatxe.util

import android.preference.PreferenceManager
import com.example.anothertimdatxe.application.CarBookingApplication
import com.example.anothertimdatxe.base.constant.RequestParam
import com.example.anothertimdatxe.entity.UserData
import com.google.gson.Gson

object CarBookingSharePreference {
    private const val USER_DATA = "user_data"
    private const val GOOGLE_MAP_API_KEY = "google_map_api_key"
    private var mUserId: Int? = null
    private var mAccessToken: String? = null


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
                return it.id
            }
        }
        return mUserId ?: -1
    }

    fun getAccessToken(): String {
        if (mAccessToken == null) getUserData()?.let { mAccessToken = RequestParam.BEARER + it.session_token }
        return mAccessToken ?: ""
    }

    fun clearAllPreference() {
        var preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        var editor = preference.edit()
        mUserId = null
        mAccessToken = null
        editor.clear()
        editor.apply()
    }
}