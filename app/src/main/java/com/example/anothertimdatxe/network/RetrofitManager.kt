package com.example.anothertimdatxe.network

import android.support.annotation.MainThread
import android.text.TextUtils
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.request.LoginRequest
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//singleton
object RetrofitManager {
    private fun createRetrofit(baseUrl: String): Retrofit {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }

    private val apiService = RetrofitManager.createRetrofit("http://api.timdatxe.com/").create(ApiService::class.java)

    fun loginUser(callBack: ICallBack<UserData>, request: LoginRequest) {
        var requestBody = RetrofitManager.createPostRequest(request)
        var subscribe = getSubcribler(callBack)
                apiService.loginUser(requestBody)
                //Observable: I/O thread
                //Observer: Main Thread
                //get return data in main thread
                //AndroidSchedulers.mainThread(): allow in UI thread
                .observeOn(AndroidSchedulers.mainThread())
                //request will be execute in I/O thread
                //Schedulers.io(): network call, file, database...
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscribe)
    }

    private fun <T> getSubcribler(callBack: ICallBack<T>): DisposableSingleObserver<BaseResult<T>> {
        return object : DisposableSingleObserver<BaseResult<T>>() {
            override fun onSuccess(t: BaseResult<T>) {
                if (t.status == 200)
                    callBack.onSuccess(t.data)
                else
                    if (TextUtils.isEmpty(t.msg))
                        callBack.onError(ApiException(""))
                    else callBack.onError(ApiException(t.msg.toString()))
            }

            override fun onError(e: Throwable) {
                callBack.onError(ApiException(e.message.toString()))
            }
        }
    }

    private fun createPostRequest(request: Any): RequestBody {
        var stringInJson = Gson().toJson(request)
        var requestBody: RequestBody = RequestBody.create(MultipartBody.FORM, stringInJson)//data is divided to many part
        return requestBody
    }
}