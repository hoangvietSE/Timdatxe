package com.example.anothertimdatxe.util

import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object NetworkUtil {
    fun handleError(throwable: Throwable) {
        if(throwable is ConnectException||throwable is UnknownHostException||throwable is TimeoutException){
            ToastUtil.show("Có lỗi xảy ra. Vui lòng thử lại")
        }
    }
}