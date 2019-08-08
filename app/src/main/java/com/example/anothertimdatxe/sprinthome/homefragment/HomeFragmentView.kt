package com.example.anothertimdatxe.sprinthome.homefragment

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse

interface HomeFragmentView : BaseView {
    fun showListHotCities(data: ArrayList<HotCitiesResponse>)
    fun showListBanners(list: List<BannerHomeResponse>)
    fun showListUserPost(list: List<UserPostResponse>)
    fun showListDriverPost(list: List<DriverPostResponse>)
    fun showLoadingData()
    fun hideLoadingData()
}