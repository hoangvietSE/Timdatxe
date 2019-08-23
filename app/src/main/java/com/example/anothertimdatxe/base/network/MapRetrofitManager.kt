package com.example.anothertimdatxe.base.network

import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.application.CarBookingApplication
import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.map.response.GoogleMapDirectionResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MapRetrofitManager {
    private fun createRetrofit(baseUrl: String): Retrofit {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(RetrofitManager.TIME, TimeUnit.SECONDS)
                .readTimeout(RetrofitManager.TIME, TimeUnit.SECONDS)
                .writeTimeout(RetrofitManager.TIME, TimeUnit.SECONDS)

        //logcat for call api
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    val mapApiService = createRetrofit(BuildConfig.MAP_BASE_URL).create(MapApiService::class.java)

    private fun <T> getSubcriber(callBack: ICallBack<T>): DisposableSingleObserver<Response<T>> {
        return object : DisposableSingleObserver<Response<T>>() {
            override fun onSuccess(response: Response<T>) {
                /*if (t.status == 200) {
                    callBack.onSuccess(t.data)
                } else {
                    if (TextUtils.isEmpty(t.msg)) {
                        callBack.onError(ApiException(""))
                    } else {
                        callBack.onError(ApiException((t.msg)))
                    }
                }*/
                handleResponse(callBack, response)
            }

            override fun onError(e: Throwable) {
                callBack.onError(ApiException(e.message, e))
            }
        }
    }

    private fun <T> handleResponse(callBack: ICallBack<T>, response: Response<T>) {
        when {
            response.code() == ApiConstant.httpStatusCode.OK -> callBack.onSuccess(response.body()!!)
            response.code() == ApiConstant.httpStatusCode.CREATE -> callBack.onSuccess(response.body()!!)
            response.code() == ApiConstant.httpStatusCode.UNAUTHORIZED -> handleErrorResponse(callBack, response)
            else -> handleErrorResponse(callBack, response)
        }
    }

    private fun <T> handleErrorResponse(callBack: ICallBack<T>, response: Response<T>) {
//        callBack.onError(ApiException("Error"))
    }

    fun fetchWayPoints(iCallBack: ICallBack<GoogleMapDirectionResponse>, origin: String, destination: String): Disposable {
        val subscribe = getSubcriber(iCallBack)
        return mapApiService.getDirection(
                origin,
                destination,
                CarBookingApplication.instance.resources.getString(R.string.google_services_api_key)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

}