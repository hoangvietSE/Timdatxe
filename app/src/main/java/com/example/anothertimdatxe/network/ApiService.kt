package com.example.anothertimdatxe.network


import com.example.anothertimdatxe.entity.UserData
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import io.reactivex.Single

interface ApiService {
    //login with user
    //Observable
    //Single Observable: only value or error
    @POST("/v1/user_sessions")
    @Headers("Content-Type: application/json")
    fun loginUser(@Body requestBody: RequestBody): Single<BaseResult<UserData>>
}