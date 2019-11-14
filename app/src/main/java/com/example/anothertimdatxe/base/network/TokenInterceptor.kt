package com.example.anothertimdatxe.base.network

import android.text.TextUtils
import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.eventbus.RefreshTokenFailure
import com.example.anothertimdatxe.util.CarBookingSharePreference
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus

class TokenInterceptor : Interceptor {
    var response: Response? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val builder = origin.newBuilder()
        val request = builder.build()
        response = chain.proceed(request)
        if (response?.code() == ApiConstant.HttpStatusCode.UNAUTHORIZED) {
            val accessToken = CarBookingSharePreference.getAccessToken()
            if (!TextUtils.isEmpty(accessToken)) {
                val responseRefreshToken = if (CarBookingSharePreference.getUserData()?.isUser!!) {
                    RetrofitManager.userRefreshTokenInterceptor(accessToken).execute()
                } else {
                    RetrofitManager.driverRefreshTokenInterceptor(accessToken).execute()
                }
                if (responseRefreshToken.code() == ApiConstant.HttpStatusCode.INTERNAL_ERROR_SERVER) {
                    EventBus.getDefault().postSticky(RefreshTokenFailure())
                }
                if (responseRefreshToken?.isSuccessful!! && responseRefreshToken.body() != null) {
                    val userData = CarBookingSharePreference.getUserData()
                    val newToken = responseRefreshToken.body()?.data?.token!!
                    userData?.session_token = newToken
                    CarBookingSharePreference.setUserData(userData!!)
                    val newRequest = origin
                            .newBuilder()
                            .header("Authorization", CarBookingSharePreference.getAccessToken())
                            .method(origin.method(), origin.body())
                            .build()
                    response = chain.proceed(newRequest!!)
                } else {
                    EventBus.getDefault().postSticky(RefreshTokenFailure())
                }
            } else {
                EventBus.getDefault().postSticky(RefreshTokenFailure())
            }
        }
        return response!!
    }
}