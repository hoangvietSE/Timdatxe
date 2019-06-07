package com.example.anothertimdatxe.base.network


import com.example.anothertimdatxe.base.ApiConstant
import com.example.anothertimdatxe.base.RequestParam
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.entity.RegisResult
import com.example.anothertimdatxe.entity.UserData
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //login with user
    //Observable
    //Single Observable: only an item or an error
    @POST(ApiConstant.USER_SESSIONS)
    @Headers("Content-Type: application/json")
    //login with driver
    fun loginUser(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    //login with driver
    @POST(ApiConstant.DRIVER_SESSIONS)
    @Headers("Content-Type: application/json")
    fun loginDriver(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    //regis with driver
    @POST(ApiConstant.DRIVER_REGISTRATIONS)
    @Headers("Content-Type: application/json")
    fun registerDriver(@Body requestBody: RequestBody): Single<Response<BaseResult<RegisResult>>>

    //active with driver
    @POST(ApiConstant.DRIVER_ACTIVATIONS)
    @Headers("Content-Type: application/json")
    fun activeDriver(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    //Request reset user password
    @POST(ApiConstant.USER_PASSWORDS)
    @Headers("Content-Type: application/json")
    fun resetUserPassword(@Body requestBody: RequestBody): Single<Response<BaseResult<ForgotResult>>>

    //Request reset driver password
    @POST(ApiConstant.DRIVER_PASSWORDS)
    @Headers("Content-Type: application/json")
    fun resetDriverPassword(@Body requestBody: RequestBody): Single<Response<BaseResult<ForgotResult>>>

    @PUT(ApiConstant.USER_PASSWORDS)
    @Headers("Content-Type: application/json")
    fun userUpdatePassword(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    @PUT(ApiConstant.DRIVER_PASSWORDS)
    @Headers("Content-Type: application/json")
    fun driverUpdatePassword(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    //user change password
    @PUT(ApiConstant.USER_CHANGE_PASSWORDS)
    @Headers("Content-Type: application/json")
    fun userChangePassword(@Path(RequestParam.ID) id: Int, @Body createPostRequest: RequestBody): Single<Response<BaseResult<UserData>>>

    @POST(ApiConstant.LOGIN_SOCIAL)
    @Headers("Content-Type: application/json")
    fun loginSocial(@Body requestBody: RequestBody) : Single<Response<BaseResult<UserData>>>
}