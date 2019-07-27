package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import com.example.anothertimdatxe.base.mvp.BasePresenter

interface CityPostPresenter : BasePresenter {
    fun getUserCityPost(city: String, isRefreshing: Boolean)
    fun getDriverCityPost(city: String, isRefreshing: Boolean)
}