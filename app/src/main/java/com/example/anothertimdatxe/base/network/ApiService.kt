package com.example.anothertimdatxe.base.network

import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.base.constant.RequestParam
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.entity.RegisResult
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.entity.response.*
import com.example.anothertimdatxe.request.DriverBookUserPostRequest
import com.example.anothertimdatxe.request.DriverFinishTripRequest
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(ApiConstant.HOT_BANNERS)
    @Headers("Content-Type: application/json")
    fun getBanners(): Single<Response<BaseResult<List<BannerHomeResponse>>>>

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
    fun userUpdateInfo(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody, @Path(RequestParam.ID) id: Int): Single<Response<BaseResult<UserUpdateInfoResponse>>>

    //driver update info
    @PUT(ApiConstant.DRIVER_INFO)
    @Headers("Content-Type: application/json")
    fun driverUpdateInfo(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody, @Path(RequestParam.ID) id: Int): Single<Response<BaseResult<DriverUpdateInfoResponse>>>

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

    //driver profile
    @GET(ApiConstant.DRIVER_INFO)
    @Headers("Content-Type: application/json")
    fun getDriverInfo(@Path(RequestParam.ID) id: Int): Single<Response<BaseResult<DriverProfileResponse>>>

    //driver profile user review
    @GET(ApiConstant.DRIVER_USER_REVIEW)
    @Headers("Content-Type: application/json")
    fun getUserReviewDriver(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Path(RequestParam.DRIVER_ID) id: Int, @Query(RequestParam.TYPE) type: String?,
                            @Query("page") page: Int): Single<BaseResult<List<UserReviewDriverResponse>>>

    //update profile
    @JvmSuppressWildcards
    @POST(ApiConstant.USER_UPDATE_INFO)
    @Multipart
    fun userUpdateProfile(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String,
                          @Path(RequestParam.ID) id: Int,
                          @Part part: MultipartBody.Part?,
                          @PartMap request: Map<String, RequestBody>): Single<BaseResult<UserData>>

    //driver update profile
    @JvmSuppressWildcards
    @POST(ApiConstant.DRIVER_INFO)
    @Multipart
    fun driverUpdateProfile(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String,
                            @Path(RequestParam.ID) id: Int,
                            @PartMap request: Map<String, RequestBody>,
                            @Part avatar: Array<MultipartBody.Part?>): Single<BaseResult<DriverDataResponse>>

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
    fun driverSearchUserPost(@QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<DriverSearchResponse>>>

    //User Post Detail
    @GET(ApiConstant.USER_POST_DETAIL)
    @Headers("Content-Type: application/json")
    fun userPostDetail(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Path(RequestParam.ID) id: Int): Single<Response<BaseResult<UserPostDetailResponse>>>

    //Driver Cancel Request
    @DELETE(ApiConstant.DRIVER_CANCEL_REQUEST)
    @Headers("Content-Type: application/json")
    fun driverCancelRequest(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Path(RequestParam.ID) id: Int): Single<BaseResult<CancelRequestResponse>>

    //Driver Cancel Booking
    @Multipart
    @POST(ApiConstant.DRIVER_CANCEL_DRIVER_BOOKING)
    fun driverCancelBooing(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Part(RequestParam.ID) id: Int): Single<BaseResult<CancelRequestResponse>>

    //Driver Finish Trip
    @HTTP(method = "DELETE", path = ApiConstant.DRIVER_FINISH_TRIP, hasBody = true)
    @Headers("Content-Type: application/json")
    fun driverFinishTrip(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body body: DriverFinishTripRequest): Single<BaseResult<CancelRequestResponse>>

    //Driver Apply User Post
    @POST(ApiConstant.DRIVER_REQUEST_USER_POST)
    @Headers("Content-Type: application/json")
    fun driverBookUserPost(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body body: DriverBookUserPostRequest): Single<BaseResult<DriverBookUserPostResponse>>

    //Driver Car Detail
    @GET(ApiConstant.DRIVER_CAR_DETAIL)
    @Headers("Content-Type: application/json")
    fun driverCarDetail(@Path(RequestParam.ID) id: Int): Single<BaseResult<DriverCarDetailResponse>>

    //Driver Car Brand
    @GET(ApiConstant.CAR_BRAND)
    @Headers("Content-Type: application/json")
    fun getDriverCarBrand(): Single<BaseResult<List<DriverCarBrandResponse>>>

    //Driver Car Brand Detail
    @GET(ApiConstant.CAR_NAME)
    @Headers("Content-Type: application/json")
    fun getDriverCarName(@Path(RequestParam.ID) id: Int): Single<BaseResult<List<DriverCarBrandDetailResponse>>>

    //User Post Home
    @GET(ApiConstant.USER_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun userPostHome(@Query(RequestParam.TYPE) type: Int): Single<BaseResult<List<UserPostResponse>>>

    //Driver Post Home
    @GET(ApiConstant.DRIVER_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun driverPostHome(@Query(RequestParam.TYPE) type: Int): Single<BaseResult<List<DriverPostResponse>>>

    //Driver List Request
    @GET(ApiConstant.DRIVER_REQUEST_USER_POST)
    @Headers("Content-Type: application/json")
    fun driverListRequest(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<DriverListRequestResponse>>>

    //Driver List Car and Number Seat
    @GET(ApiConstant.DRIVER_CAR)
    @Headers("Content-Type: application/json")
    fun driverCarInfo(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String): Single<Response<BaseResult<List<DriverCarResponse>>>>

    //Driver Create Post
    @POST(ApiConstant.DRIVER_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun driverCreatePost(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Body requestBody: RequestBody): Single<Response<BaseResult<DriverCreatePostResponse>>>

    //Driver List Post
    @GET(ApiConstant.DRIVER_LIST_POST)
    @Headers("Content-Type: application/json")
    fun driverListPost(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String,
                       @Path(RequestParam.ID) id: Int,
                       @QueryMap data: MutableMap<String, Any>): Single<BaseResult<List<DriverListPostResponse>>>

    //Driver List Car and Number Seat
    @GET(ApiConstant.DRIVER_CAR)
    @Headers("Content-Type: application/json")
    fun driverCarInfoV1(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String): Single<BaseResult<List<DriverCarResponse>>>

    //Driver Detail Post
    @GET(ApiConstant.DRIVER_POST_DETAIL)
    @Headers("Content-Type: application/json")
    fun getDriverPostDetailV1(@Path(RequestParam.ID) id: Int): Single<BaseResult<DriverPostDetailResponse>>

    //User List Post
    @GET(ApiConstant.USER_LIST_POST_CREATED)
    @Headers("Content-Type: application/json")
    fun getUserListPost(@Header(RequestParam.AUTHORIZATION_HEADER) authToken: String, @Path(RequestParam.USER_ID) user_id: Int, @QueryMap data: MutableMap<String, Any?>): Single<BaseResult<List<UserListPostEntity>>>
}