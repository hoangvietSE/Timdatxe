package com.example.anothertimdatxe.sprintlogin.changepassword


import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.BaseResult
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.request.ChangePasswordRequest
import io.reactivex.disposables.Disposable

class ChangePasswordActivityImpl(mView: ChangePasswordView) : ChangePasswordPresenter,
    BasePresenterImpl<ChangePasswordView>(mView) {

    override fun userChangePassword(password: String, new_password: String) {
        mView!!.showLoading()
        val request = ChangePasswordRequest()
        request.password = password
        request.new_password = new_password
        var disposable: Disposable = RetrofitManager.userChangePassWord(object : ICallBack<BaseResult<UserData>> {
            override fun onSuccess(result: BaseResult<UserData>?) {
                mView!!.hideLoading()
                Toast.makeText(mView as Context, (mView as Context).resources.getString(R.string.change_password_success), LENGTH_LONG).show()
            }

            override fun onError(e: ApiException) {
                mView!!.hideLoading()
                Toast.makeText(mView as Context, e.message, LENGTH_LONG).show()
            }
        }, request)
    }

}