package com.example.anothertimdatxe.base.network

import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.entity.ForgotResult
import com.example.anothertimdatxe.entity.RegisResult
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.entity.response.*
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.request.*
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//singleton
object RetrofitManager {
    const val TIME: Long = 60
    private fun createRetrofit(baseUrl: String): Retrofit {
        //timeout for connection is 120s
        var client = OkHttpClient.Builder()
                .writeTimeout(TIME, TimeUnit.SECONDS)
                .readTimeout(TIME, TimeUnit.SECONDS)
                .connectTimeout(TIME, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            var logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }
        client.addInterceptor(TokenInterceptor())

        return Retrofit.Builder()
                .client(client.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //this verifies that using RxJava2 for this API call
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val apiService = createRetrofit(BuildConfig.BASE_URL).create(ApiService::class.java)

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

    //POJO Object to Json
    //convert request object to JSON and add to body of POST HTTP request
    private fun createPostRequest(request: Any): RequestBody {
        var requestInJson: String = Gson().toJson(request)
        return RequestBody.create(MultipartBody.FORM, requestInJson) //data is divided to many part
    }

    private fun createRequestBody(data: Any): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), data.toString())
    }

    //Utilize RxJava2 to run this on another thread and get result in Main Thread by Obverser
    fun loginUser(callBack: ICallBack<BaseResult<UserData>>, request: LoginRequest): Disposable {
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

    fun getBanners(callBack: ICallBack<BaseResult<List<BannerHomeResponse>>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun loginDriver(callBack: ICallBack<BaseResult<UserData>>, request: LoginRequest): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.loginDriver(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscribe)
    }

    fun registerDriver(callBack: ICallBack<BaseResult<RegisResult>>, request: RegisterRequest): Disposable {
        val subcriber = getSubcriber(callBack)
        return apiService.registerDriver(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subcriber)
    }

    fun userUpdateInfo(callBack: ICallBack<BaseResult<UserUpdateInfoResponse>>, userData: UserData): Disposable {
        val subcriber = getSubcriber(callBack)
        return apiService.userUpdateInfo(CarBookingSharePreference.getAccessToken(), createPostRequest(userData), userData.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subcriber)
    }

    fun driverUpdateInfo(callBack: ICallBack<BaseResult<DriverUpdateInfoResponse>>, userData: UserData): Disposable {
        val subcriber = getSubcriber(callBack)
        return apiService.driverUpdateInfo(CarBookingSharePreference.getAccessToken(), createPostRequest(userData), userData.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subcriber)
    }

    fun activeDriver(callBack: ICallBack<BaseResult<UserData>>, request: ActiveRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.activeDriver(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun resetUserPassword(callBack: ICallBack<BaseResult<ForgotResult>>, request: ForgotRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.resetUserPassword(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun resetDriverPassword(callBack: ICallBack<BaseResult<ForgotResult>>, request: ForgotRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.resetDriverPassword(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun userUpdatePassword(callBack: ICallBack<BaseResult<UserData>>, request: UpdatePasswordRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.userUpdatePassword(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun driverUpdatePassword(callBack: ICallBack<BaseResult<UserData>>, request: UpdatePasswordRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.userUpdatePassword(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    //user change password
    fun userChangePassWord(callBack: ICallBack<BaseResult<UserData>>, request: ChangePasswordRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.userChangePassword(
                CarBookingSharePreference.getUserId(),
                createPostRequest(request)
        )//id of viethoangtien@gmail.com
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun loginSocial(callBack: ICallBack<BaseResult<UserData>>, request: LoginFacebookRequest): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.loginSocial(createPostRequest(request))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun onGetFaqs(callBack: ICallBack<BaseResult<List<FaqsResponse>>>, request: MutableMap<String, Any>): Disposable {
        val subcriber = getSubcriber(callBack)
        return apiService.onGetFaqs(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subcriber)
    }

    fun getUserPostCreated(callBack: ICallBack<BaseResult<List<UserListPostEntity>>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getUserPostCreated(CarBookingSharePreference.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun getUserInfo(callBack: ICallBack<BaseResult<UserData>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getUserInfo(CarBookingSharePreference.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
    }

    fun getDriverInfo(callBack: ICallBack<BaseResult<DriverProfileResponse>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getDriverInfo(CarBookingSharePreference.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
    }

    fun getUserReviewDriver(pageIndex: Int): Single<BaseResult<List<UserReviewDriverResponse>>> {
        return apiService.getUserReviewDriver(CarBookingSharePreference.getAccessToken(), CarBookingSharePreference.getUserId(), null, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun userUpdateProfile(part: MultipartBody.Part?, request: Map<String, RequestBody>): Single<BaseResult<UserData>> {
        return apiService.userUpdateProfile(CarBookingSharePreference.getAccessToken(), CarBookingSharePreference.getUserId(),
                part, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverUpdateProfile(request: Map<String, RequestBody>, partArray: Array<MultipartBody.Part?>): Single<BaseResult<DriverDataResponse>> {
        return apiService.driverUpdateProfile(CarBookingSharePreference.getAccessToken(),
                CarBookingSharePreference.getUserId(),
                request,
                partArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getListPostFindUser(request: MutableMap<String, Any>): Single<BaseResult<List<DriverPostResponse>>> {
        return apiService.getListPostFindUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getListPostFindCar(request: MutableMap<String, Any>): Single<BaseResult<List<UserPostResponse>>> {
        return apiService.getListPostFindCar(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserHistory(callBack: ICallBack<BaseResponse<List<UserHistoryResponse>>>, id: Int): Disposable {
        var subscribe = getSubcriber(callBack)
        return apiService.getUserHistory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getDriverHistory(callBack: ICallBack<BaseResponse<List<DriverHistoryResponse>>>, id: Int): Disposable {
        var subscribe = getSubcriber(callBack)
        return apiService.getDriverHistory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getDriverRevenue(callBack: ICallBack<BaseRevenueResponse<List<DriverRevenueResponse>>>, month: Int): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getDriverRevenue(CarBookingSharePreference.getAccessToken(), CarBookingSharePreference.getUserId(), month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun sendContactSystem(callBack: ICallBack<BaseResult<ContactSystemResponse>>, request: ContactRequest): Disposable {
        val subscribe = getSubcriber(callBack)
        val request = createPostRequest(request)
        return apiService.sendContactSystem(CarBookingSharePreference.getAccessToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getVersionApp(callBack: ICallBack<BaseResult<List<VersionAppResponse>>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getVersionApp()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun getTermAndCondition(callBack: ICallBack<BaseResult<TermAndConditionResponse>>, slug: String): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getTermAndCondition(slug)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscriber)
    }

    fun getListHotCities(callBack: ICallBack<BaseResult<ArrayList<HotCitiesResponse>>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getHotCities(CarBookingSharePreference.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getUserSearchCityPost(data: MutableMap<String, Any>): Single<BaseResult<List<SearchCityPostResponse>>> {
        return apiService.getUserSearchCityPost(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDriverSearchCityPost(data: MutableMap<String, Any>): Single<BaseResult<List<SearchCityPostResponse>>> {
        return apiService.getDriverSearchCityPost(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    //user refresh token
    fun userRefreshToken(session_token: String): Single<BaseResult<RefreshTokenResponse>> {
        return apiService.userRefreshToken(session_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    //driver refresh token
    fun driverRefreshToken(session_token: String): Single<BaseResult<RefreshTokenResponse>> {
        return apiService.driverRefreshToken(session_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    //driver search user post
    fun driverSearchUserPost(data: MutableMap<String, Any>): Single<BaseResult<List<DriverSearchResponse>>> {
        return apiService.driverSearchUserPost(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun userPostDetail(callBack: ICallBack<BaseResult<UserPostDetailResponse>>, id: Int): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.userPostDetail(CarBookingSharePreference.getAccessToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun driverCancelRequest(driverBookOptionId: Int): Single<BaseResult<CancelRequestResponse>> {
        return apiService.driverCancelRequest(CarBookingSharePreference.getAccessToken(), driverBookOptionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCancelBooking(driverBookId: Int): Single<BaseResult<CancelRequestResponse>> {
        return apiService.driverCancelBooing(CarBookingSharePreference.getAccessToken(), driverBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverFinishTrip(request: DriverFinishTripRequest): Single<BaseResult<CancelRequestResponse>> {
        return apiService.driverFinishTrip(CarBookingSharePreference.getAccessToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverBookUserPost(request: DriverBookUserPostRequest): Single<BaseResult<DriverBookUserPostResponse>> {
        return apiService.driverBookUserPost(CarBookingSharePreference.getAccessToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCarDetail(driverCarId: Int): Single<BaseResult<DriverCarDetailResponse>> {
        return apiService.driverCarDetail(driverCarId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun userPostHome(): Single<BaseResult<List<UserPostResponse>>> {
        return apiService.userPostHome(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverPostHome(): Single<BaseResult<List<DriverPostResponse>>> {
        return apiService.driverPostHome(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCarBrand(): Single<BaseResult<List<DriverCarBrandResponse>>> {
        return apiService.getDriverCarBrand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCarName(id: Int): Single<BaseResult<List<DriverCarBrandDetailResponse>>> {
        return apiService.getDriverCarName(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchListDriverBook(data: MutableMap<String, Any>): Single<BaseResult<List<DriverListRequestResponse>>> {
        return apiService.driverListRequest(
                CarBookingSharePreference.getAccessToken(),
                data
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCarInfo(callBack: ICallBack<BaseResult<List<DriverCarResponse>>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.driverCarInfo(CarBookingSharePreference.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun driverCreatePost(callBack: ICallBack<BaseResult<DriverCreatePostResponse>>, request: DriverCreatePostRequest): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.driverCreatePost(CarBookingSharePreference.getAccessToken(), createPostRequest(request))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun driverListPost(data: MutableMap<String, Any>): Single<BaseResult<List<DriverListPostResponse>>> {
        return apiService.driverListPost(
                CarBookingSharePreference.getAccessToken(),
                CarBookingSharePreference.getUserId(),
                data
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverCarInfoV1(): Single<BaseResult<List<DriverCarResponse>>> {
        return apiService.driverCarInfoV1(CarBookingSharePreference.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun driverPostDetailV1(driverId: Int): Single<BaseResult<DriverPostDetailResponse>> {
        return apiService.getDriverPostDetailV1(driverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserListPost(data: MutableMap<String, Any?>): Single<BaseResult<List<UserListPostEntity>>> {
        return apiService.getUserListPost(CarBookingSharePreference.getAccessToken(), CarBookingSharePreference.getUserId(), data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserListBook(data: MutableMap<String, Any?>): Single<BaseResult<List<ListUserBookResponse>>> {
        return apiService.getListUserBook(CarBookingSharePreference.getAccessToken(), data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchUserRequestDetail(postId: Int, callBack: ICallBack<BaseResult<UserRequestDetailResponse>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.getUserRequestDetail(CarBookingSharePreference.getAccessToken(), postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
    }

    fun userCancelUserBook(userBookId: Int, callBack: ICallBack<BaseResult<UserCancelUserBookResponse>>): Disposable {
        val subscriber = getSubcriber(callBack)
        return apiService.userCancelUserBook(CarBookingSharePreference.getAccessToken(), createRequestBody(userBookId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
    }

    fun userCreatePost(data: UserCreatePostRequest, callBack: ICallBack<BaseResult<UserCreatePostResponse>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.userCreatePost(CarBookingSharePreference.getAccessToken(), createPostRequest(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getUserPostDetail(userPostId: Int, callBack: ICallBack<BaseResult<PostDetailResponse>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getUserPostDetail(CarBookingSharePreference.getAccessToken(), userPostId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun deleteUserPost(userPostId: Int, callBack: ICallBack<BaseResult<PostDetailResponse>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.deleteUserPost(CarBookingSharePreference.getAccessToken(), userPostId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getUserBookDetail(postId: Int, callBack: ICallBack<BaseResult<DriverPostDetailResponse>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getUserBookDetail(CarBookingSharePreference.getAccessToken(), postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun getUserConfirmBooking(driverPostId: Int, callBack: ICallBack<BaseResult<ConfirmBookingResponse>>): Disposable {
        val subscribe = getSubcriber(callBack)
        return apiService.getUserConfirmBooking(CarBookingSharePreference.getAccessToken(), driverPostId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscribe)
    }

    fun userRefreshTokenInterceptor(accessToken: String): Call<BaseResponse<RefreshTokenResponse>> {
        return apiService.userRefreshTokenInterceptor(accessToken)
    }

    fun driverRefreshTokenInterceptor(accessToken: String): Call<BaseResponse<RefreshTokenResponse>> {
        return apiService.driverRefreshTokenInterceptor(accessToken)
    }
}