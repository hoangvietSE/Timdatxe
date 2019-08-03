package com.example.anothertimdatxe.base.network


import com.example.anothertimdatxe.base.ApiConstant
import com.example.anothertimdatxe.base.RequestParam
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.entity.RegisResult
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.entity.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
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

    //user update info
    @PUT(ApiConstant.USER_INFO)
    @Headers("Content-Type: application/json")
    fun userUpdateInfo(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody, @Path(RequestParam.ID) id: Int) : Single<Response<BaseResult<UserUpdateInfoResponse>>>

    //driver update info
    @PUT(ApiConstant.DRIVER_INFO)
    @Headers("Content-Type: application/json")
    fun driverUpdateInfo(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody, @Path(RequestParam.ID) id: Int) : Single<Response<BaseResult<DriverUpdateInfoResponse>>>

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
    fun loginSocial(@Body requestBody: RequestBody): Single<Response<BaseResult<UserData>>>

    //Faqs
    @GET(ApiConstant.SHOW_FAQS)
    @Headers("Content-Type: application/json")
    fun onGetFaqs(@QueryMap data: MutableMap<String, Any>
    ): Single<Response<BaseResult<List<FaqsResponse>>>>

    //get user post created
    @GET(ApiConstant.USER_LIST_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun getUserPostCreated(@Path(RequestParam.USER_ID) user_id: Int): Single<Response<BaseResult<List<UserListPostEntity>>>>

    //user profile
    @GET(ApiConstant.USER_INFO)
    @Headers("Content-Type: application/json")
    fun getUserInfo(@Path(RequestParam.ID) id: Int): Single<Response<BaseResult<UserData>>>

    //update profile
    @JvmSuppressWildcards
    @POST(ApiConstant.USER_UPDATE_INFO)
    @Multipart
    fun userUpdateProfile(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String,
                          @Path(RequestParam.ID) id: Int,
                          @Part part: MultipartBody.Part?,
                          @PartMap request: Map<String, RequestBody>): Single<BaseResult<UserData>>

    //post more find user
    @GET(ApiConstant.DRIVER_POST_CREATED)
    @JvmSuppressWildcards
    @Headers("Content-Type: application/json")
    fun getListPostFindUser(@QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<DriverPostResponse>>>

    //post more find car
    @GET(ApiConstant.USER_POST_CREATED)
    @JvmSuppressWildcards
    @Headers("Content-Type: application/json")
    fun getListPostFindCar(@QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<UserPostResponse>>>

    //User History
    @GET(ApiConstant.USER_HISTORY)
    @Headers("Content-Type: application/json")
    fun getUserHistory(@Path(RequestParam.ID) id: Int): Single<Response<BaseResponse<List<UserHistoryResponse>>>>

    //Driver History
    @GET(ApiConstant.DRIVER_HISTORY)
    @Headers("Content-Type: application/json")
    fun getDriverHistory(@Path(RequestParam.ID) id: Int): Single<Response<BaseResponse<List<DriverHistoryResponse>>>>

    //Driver Revenue
    @GET(ApiConstant.DRIVER_REVENUE)
    @Headers("Content-Type: application/json")
    fun getDriverRevenue(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Path(RequestParam.ID) id: Int, @Query("month") month: Int): Single<Response<BaseRevenueResponse<List<DriverRevenueResponse>>>>

    //Contact System
    @POST(ApiConstant.CONTACT_SYSTEM)
    @Headers("Content-Type: application/json")
    fun sendContactSystem(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody): Single<Response<BaseResult<ContactSystemResponse>>>

    @GET(ApiConstant.VERSION_APP)
    @Headers("Content-Type: application/json")
    fun getVersionApp(): Single<Response<BaseResult<List<VersionAppResponse>>>>

    @GET(ApiConstant.TERM_AND_CONDITION)
    @Headers("Content-Type: application/json")
    fun getTermAndCondition(@Path(RequestParam.SLUG) slug: String): Single<Response<BaseResult<TermAndConditionResponse>>>

    @GET(ApiConstant.HOT_CITIES)
    @Headers("Content-Type: application/json")
    fun getHotCities(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String): Single<Response<BaseResult<ArrayList<HotCitiesResponse>>>>

    //User Hot Cities
    @GET(ApiConstant.USER_SEARCH_CITY_POST)
    @JvmSuppressWildcards
    @Headers("Content-Type: application/json")
    fun getUserSearchCityPost(@QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<SearchCityPostResponse>>>

    //Driver Hot Cities
    @GET(ApiConstant.DRIVER_SEARCH_CITY_POST)
    @JvmSuppressWildcards
    @Headers("Content-Type: application/json")
    fun getDriverSearchCityPost(@QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<SearchCityPostResponse>>>

    //User Refresh Token
    @GET(ApiConstant.USER_REFRESH_TOKEN)
    @Headers("Content-Type: application/json")
    fun userRefreshToken(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String): Single<BaseResult<RefreshTokenResponse>>

    //Driver Refresh Token
    @GET(ApiConstant.DRIVER_REFRESH_TOEKN)
    @Headers("Content-Type: application/json")
    fun driverRefreshToken(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String): Single<BaseResult<RefreshTokenResponse>>

    //Driver Search User Post
    @GET(ApiConstant.USER_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun driverSearchUserPost(@QueryMap data: MutableMap<String, Any>) : Single<BaseResult<List<DriverSearchResponse>>>
}