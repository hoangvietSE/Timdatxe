package com.example.anothertimdatxe.sprintlogin.login

import android.content.Context
import android.support.design.widget.BaseTransientBottomBar.LENGTH_LONG
import android.widget.Toast
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.base.network.ApiException
import com.example.anothertimdatxe.base.network.ICallBack
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.request.LoginRequest

class LoginPresenterImpl(mView: LoginView) : BasePresenterImpl<LoginView>(mView), LoginPresenter {
    override fun login(email: String, password: String) {
        //do-something
    }

    override fun loginUser(email: String, password: String) {
        //Request Object will be pass into @Body of request method
        val request = LoginRequest()
        request.email = email
        request.password = password
        request.remember = 1
        var disposable = RetrofitManager.loginUser(object : ICallBack<UserData> {
            override fun onError(e: ApiException) {
                Toast.makeText(mView as Context, e.message, LENGTH_LONG).show()
            }

            override fun onSuccess(result: UserData?) {
                Toast.makeText(mView as Context, "Login Success", LENGTH_LONG).show()
            }
        }, request)
    }

    override fun loginDriver(email: String, password: String) {
        //do-something

    }
}