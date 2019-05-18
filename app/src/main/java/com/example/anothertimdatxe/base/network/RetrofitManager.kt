package com.example.anothertimdatxe.base.network

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
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//singleton
object RetrofitManager {
    private fun createRetrofit(baseUrl: String): Retrofit {
        //timeout for connection is 120s
        var client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //this verifies that using RxJava2 for this API call
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val apiService = createRetrofit("http://api.timdatxe.com/").create(ApiService::class.java)

    //Utilize RxJava2 to run this on another thread and get result in Main Thread by Obverser
    fun loginUser(callBack: ICallBack<UserData>, request: LoginRequest): Disposable {
        val requestBody = createPostRequest(request)
        val subcribe = getSubcriber(callBack)
        var disposable: Disposable = apiService.loginUser(requestBody)
                //Observable: I/O thread
                //request will be execute in I/O thread
                //Schedulers.io(): network call, file, database...
                .observeOn(AndroidSchedulers.mainThread())
                //Observer: Main Thread
                //get return data in main thread
                //AndroidSchedulers.mainThread(): allow in UI thread
                .subscribeOn(Schedulers.io())
                .subscribeWith(subcribe)
        return disposable
    }

    private fun <T> getSubcriber(callBack: ICallBack<T>): DisposableSingleObserver<BaseResult<T>> {
        return object : DisposableSingleObserver<BaseResult<T>>() {
            override fun onSuccess(t: BaseResult<T>) {
                if (t.status == 200) {
                    callBack.onSuccess(t.data)
                } else {
                    if (TextUtils.isEmpty(t.msg)) {
                        callBack.onError(ApiException(""))
                    } else {
                        callBack.onError(ApiException((t.msg)))
                    }
                }
            }

            override fun onError(e: Throwable) {
                callBack.onError(ApiException(e.message))
            }
        }
    }

    fun loginDriver(callBack: ICallBack<UserData>, request: LoginRequest) {
        //convert request object to JSON and add to body of POST HTTP request
    }

    //POJO Object to Json
    private fun createPostRequest(request: Any): RequestBody {
        var requestInJson: String = Gson().toJson(request)
        return RequestBody.create(MultipartBody.FORM, requestInJson) //data is divided to many part
    }
}