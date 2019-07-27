package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.SearchCityPostResponse

interface CityPostView : BaseView {
    fun showLoadingIndicatior()
    fun hideLoadingIndicator()
    fun showEmptyContent(check: Boolean)
    fun showUserCityPost(data: List<SearchCityPostResponse>, isRefreshing: Boolean)
    fun showDriverCityPost(data: List<SearchCityPostResponse>, isRefreshing: Boolean)
    fun enableLoadingMore()
    fun disableLoadingMore()
    fun showLoadingMoreProgress()
    fun hideLoadingMoreProgress()
}