package com.example.anothertimdatxe.util

import android.preference.PreferenceManager
import com.example.anothertimdatxe.application.CarBookingApplication
import com.example.anothertimdatxe.base.constant.RequestParam
import com.example.anothertimdatxe.entity.UserData
import com.google.gson.Gson

object CarBookingSharePreference {
    private const val USER_DATA = "user_data"
    private const val GOOGLE_MAP_API_KEY = "google_map_api_key"
    private const val WELCOME_DRIVER_APP = "welcome_driver_app"
    private const val WELCOME_USER_APP = "welcome_user_app"
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

    fun getWelcomeDriverApp(): Boolean {
        val preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        val isWelcome = preference.getBoolean(WELCOME_DRIVER_APP, true)
        return isWelcome
    }

    fun setWelcomeDriverApp() {
        val preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        val editor = preference.edit()
        editor.putBoolean(WELCOME_DRIVER_APP, false)
        editor.apply()
    }

    fun getWelcomeUserApp(): Boolean {
        val preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        val isWelcome = preference.getBoolean(WELCOME_USER_APP, true)
        return isWelcome
    }

    fun setWelcomeUserApp() {
        val preference = PreferenceManager.getDefaultSharedPreferences(CarBookingApplication.instance)
        val editor = preference.edit()
        editor.putBoolean(WELCOME_USER_APP, false)
        editor.apply()
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
        editor.putBoolean(WELCOME_USER_APP, false)
        editor.putBoolean(WELCOME_DRIVER_APP, false)
        editor.apply()
    }
}