package com.example.anothertimdatxe.splash

import android.content.Context
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.constant.ApiConstant
import com.example.anothertimdatxe.base.network.NetworkConnectionInterceptor
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.DeviceUtil
import io.reactivex.disposables.CompositeDisposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SplashPresenterImpl(private var mView: SplashView) : SplashPresenter {
    private var mUserData: UserData? = CarBookingSharePreference.getUserData()
    private var mCompositeDisposable = CompositeDisposable()
    override fun refreshToken() {
        if (mUserData == null) {
            mView!!.goToLoginScreen()
        } else {
            if (DeviceUtil.isConnectedToNetword(mView as Context)) {
                if (CarBookingSharePreference.getUserData()!!.isUser) {
                    userRefreshToken(mUserData!!)
                } else {
                    driverRefreshToken(mUserData!!)
                }
            } else {
                mView!!.showNoConnectedToNetword()
            }
        }
    }

    fun userRefreshToken(userData: UserData) {
        val sessionToken = userData.session_token
        val disposable = RetrofitManager.userRefreshToken("Bearer " + sessionToken)
                .doOnSubscribe {
                    if (mView != null) {
                        mView.showLoading()
                    }
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            if (it.status == ApiConstant.httpStatusCode.OK) {
                                userData.session_token = it.data?.token!!
                                CarBookingSharePreference.setUserData(userData)
                                if (userData.full_name.isNullOrEmpty()) {
                                    mView!!.goToUpdateInfoScreen()
                                } else {
                                    mView!!.goToHomeScreen()
                                }
                            } else if (it.status == ApiConstant.httpStatusCode.INTERNAL_ERROR_SERVER) {
                                //Phiên đăng nhập hết, login again
                                mView!!.showDialogExpiredSessionLogin()
                            }
                        },
                        {
                            handlerError(it)
                        }
                )
        mCompositeDisposable.add(disposable)
    }

    fun driverRefreshToken(userData: UserData) {
        val sessionToken = userData.session_token
        val disposable = RetrofitManager.driverRefreshToken("Bearer " + sessionToken)
                .doOnSubscribe {
                    if (mView != null) {
                        mView.showLoading()
                    }
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            if (it.status == ApiConstant.httpStatusCode.OK) {
                                userData.session_token = it.data?.token!!
                                CarBookingSharePreference.setUserData(userData)
                                if (userData.full_name.isNullOrEmpty()) {
                                    mView!!.goToUpdateInfoScreen()
                                } else {
                                    mView!!.goToHomeScreen()
                                }
                            } else if (it.status == ApiConstant.httpStatusCode.INTERNAL_ERROR_SERVER) {
                                //Phiên đăng nhập hết, login again
                                mView!!.showDialogExpiredSessionLogin()
                            }
                        },
                        {
                            handlerError(it)
                        }
                )
    }

    fun handlerError(mThrowable: Throwable) {
        if (mThrowable is NetworkConnectionInterceptor.NoConnectivityException) {
            mView!!.refreshTokenError((mView as Context).resources.getString(R.string.no_connectivity_exception))
        } else if (mThrowable is ConnectException || mThrowable is UnknownHostException || mThrowable is SocketTimeoutException) {
            mView!!.refreshTokenError((mView as Context).resources.getString(R.string.server_error))
        } else {
            mView!!.showDialogExpiredSessionLogin()
        }
    }

    override fun destroy() {
        mCompositeDisposable.clear()
    }
}