package com.example.anothertimdatxe.sprinthome.homefragment

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse

interface HomeFragmentView : BaseView {
    fun showListHotCities(data: ArrayList<HotCitiesResponse>)
    fun showListBanners(list: List<BannerHomeResponse>)
}