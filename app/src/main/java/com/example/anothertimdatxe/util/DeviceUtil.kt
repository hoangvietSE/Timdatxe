package com.example.anothertimdatxe.util

import android.content.Context
import android.net.ConnectivityManager

object DeviceUtil {
    fun isConnectedToNetword(context: Context): Boolean {
        var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isConnected = false
        if (connectivityManager != null) {
            var activeNetwork = connectivityManager.activeNetworkInfo
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting)
        }
        return isConnected
    }
}