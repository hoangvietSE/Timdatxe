package com.example.anothertimdatxe.sprintlogin.login

import android.util.Log
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.network.ApiException
import com.example.anothertimdatxe.network.ICallBack
import com.example.anothertimdatxe.network.RetrofitManager
import com.example.anothertimdatxe.request.LoginRequest
import java.lang.StringBuilder

class LoginPresenterImpl : LoginPresenter {
    override fun start() {

    }

    override fun destroy() {

    }

    override fun login(email: String, password: String) {
        //do-something
    }

    override fun loginUser(email: String, password: String) {
        //Request Object will be pass into @Body of request method
        val request = LoginRequest()
        request.email = email
        request.password = password
        request.remember = 1
        val disposable = RetrofitManager.loginUser(object : ICallBack<UserData>{
            override fun onSuccess(result: UserData?) {
                var content = StringBuilder("")
                content.append("Email: ${result?.email}")
                content.append("Email: ${result?.phone}")
                Log.d("myLog",content.toString())
            }

            override fun onError(e: ApiException) {
                Log.d("myLog",e.message)
            }

        }, request)
    }

    override fun loginDriver(email: String, password: String) {
        //do-something
        Log.d("myLog", "Success")
    }
}