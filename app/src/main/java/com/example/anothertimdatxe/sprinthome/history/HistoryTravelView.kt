package com.example.anothertimdatxe.sprinthome.history

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverHistoryResponse
import com.example.anothertimdatxe.entity.response.UserHistoryResponse

interface HistoryTravelView : BaseView {
    fun showUserHistory(data: List<UserHistoryResponse>, isFreshing: Boolean)
    fun showDriverHistory(data: List<DriverHistoryResponse>, isFreshing: Boolean)
    fun setNumberTrip(count: Int)
}