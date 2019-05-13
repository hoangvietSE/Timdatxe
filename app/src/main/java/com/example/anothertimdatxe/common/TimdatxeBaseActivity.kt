package com.example.anothertimdatxe.common

import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.mvp.BasePresenter

abstract class TimdatxeBaseActivity<T : BasePresenter> : BaseActivity<T>() {
    
}