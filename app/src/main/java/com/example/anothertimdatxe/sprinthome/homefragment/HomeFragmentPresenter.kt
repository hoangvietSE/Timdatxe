package com.example.anothertimdatxe.sprinthome.homefragment

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface HomeFragmentPresenter : BasePresenter {
    fun getData(isRefreshing: Boolean)
}