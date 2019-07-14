package com.example.anothertimdatxe.sprinthome.history

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverHistoryResponse
import com.example.anothertimdatxe.entity.response.UserHistoryResponse

interface HistoryTravelView : BaseView {
    fun showUserHistory(data: List<UserHistoryResponse>)
    fun showDriverHistory(data: List<DriverHistoryResponse>)
}