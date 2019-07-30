package com.example.anothertimdatxe.sprinthome.history

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface HistoryTravelPresenter : BasePresenter {
    fun getData(isHistoryUser: Boolean, isFreshing: Boolean)
}