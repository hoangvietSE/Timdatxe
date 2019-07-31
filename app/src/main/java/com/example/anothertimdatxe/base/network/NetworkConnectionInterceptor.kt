package com.example.anothertimdatxe.base.network

import android.content.Context
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.util.DeviceUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private var mContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (DeviceUtil.isConnectedToNetword(mContext)) {
            val builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }
        throw NoConnectivityException()
    }

    inner class NoConnectivityException : IOException() {
        override val message: String?
            get() = mContext.resources.getString(R.string.no_connectivity_exception)
    }
}