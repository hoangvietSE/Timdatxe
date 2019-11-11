package com.example.anothertimdatxe.base.network

import android.text.TextUtils
import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.entity.response.BaseResponse
import com.example.anothertimdatxe.entity.response.RefreshTokenResponse
import com.example.anothertimdatxe.eventbus.RefreshTokenFailure
import com.example.anothertimdatxe.util.CarBookingSharePreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.greenrobot.eventbus.EventBus

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        var request: Request? = null
        var response = chain.proceed(original)
        when (response.code()) {
            ApiConstant.httpStatusCode.UNAUTHORIZED -> {
                val accessToken = CarBookingSharePreference.getAccessToken()
                if (!TextUtils.isEmpty(accessToken)) {
                    val callback = object : ICallBack<BaseResponse<RefreshTokenResponse>> {
                        override fun onSuccess(result: BaseResponse<RefreshTokenResponse>?) {
                            val userData = CarBookingSharePreference.getUserData()
                            userData?.session_token = result?.data?.token!!
                            CarBookingSharePreference.setUserData(userData!!)
                            original = chain.request()
                            request = original.newBuilder()
                                    .addHeader("Authorization", CarBookingSharePreference.getAccessToken())
                                    .method(original.method(), original.body())
                                    .build()
                            response = chain.proceed(request)
                        }

                        override fun onError(e: ApiException) {
                            EventBus.getDefault().postSticky(RefreshTokenFailure())
                        }

                    }
                    if (CarBookingSharePreference.getUserData()?.isUser!!) {
                        RetrofitManager.userRefreshTokenInterceptor(accessToken, callback)
                    } else {
                        RetrofitManager.driverRefreshTokenInterceptor(accessToken, callback)
                    }
                } else {
                    EventBus.getDefault().postSticky(RefreshTokenFailure())
                }
            }

        }
        return response
    }
}