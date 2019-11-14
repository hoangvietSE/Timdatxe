package com.example.anothertimdatxe.sprinthome.updateprofile

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.util.CarBookingSharePreference
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import java.io.File

class UpdateProfilePresenterImpl(mView: UpdateProfileView) : BasePresenterImpl<UpdateProfileView>(mView), UpdateProfilePresenter {
    private var mFilePart: File? = null
    private var part: MultipartBody.Part? = null
    private var request: MutableMap<String, RequestBody> = mutableMapOf()
    override fun updateUserProfile(data: UserData) {
        if (data.fullName.isEmpty() || data.fullName.isNullOrBlank()) {
            mView!!.onFullNameError()
            return
        } else if (data.address.isNullOrEmpty() || data.address.isNullOrBlank()) {
            mView!!.onAddressError()
            return
        } else if (data.description.isNullOrEmpty() || data.description.isNullOrBlank()) {
            mView!!.onDescriptionError()
            return
        }
        if (mFilePart != null) {
            val partRequestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), mFilePart)
            part = MultipartBody.Part.createFormData("app_avatar", mFilePart!!.name, partRequestBody)
        }
        request["fullName"] = createRequestBody(data.fullName)
        request["birthday"] = createRequestBody(data.birthday!!)
        request["gender"] = createRequestBody(data.gender!!)
        request["address"] = createRequestBody(data.address!!)
        request["phone"] = createRequestBody(data.phone)
        request["description"] = createRequestBody(data.description!!)
        request["_method"] = createRequestBody("PUT")
        val disposable = RetrofitManager.userUpdateProfile(part, request)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            mView!!.backUserProfile()
                            val userData = CarBookingSharePreference.getUserData()
                            userData!!.avatar = it.data!!.avatar
                            userData.fullName = it.data!!.fullName
                            userData.birthday = it.data!!.birthday
                            userData.address = it.data!!.address
                            CarBookingSharePreference.setUserData(userData)
                            EventBus.getDefault().postSticky(it.data)
                        },
                        {
                            mView!!.onUpdateProfileError()
                        }
                )
        addDispose(disposable)
    }

    override fun setFilePart(file: String) {
        mFilePart = File(file)
    }

    fun createRequestBody(data: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), data)
    }
}