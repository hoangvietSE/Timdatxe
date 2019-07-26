package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.DriverSearchCityPostResponse
import com.example.anothertimdatxe.entity.response.UserSearchCityPostResponse

interface CityPostView : BaseView {
    fun showLoadingIndicatior()
    fun hideLoadingIndicator()
    fun showEmptyContent(check: Boolean)
    fun showUserCityPost(data: List<UserSearchCityPostResponse>)
    fun showDriverCityPost(data: List<DriverSearchCityPostResponse>)
}