package com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SearchCityPostAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.DriverSearchCityPostResponse
import com.example.anothertimdatxe.entity.response.UserSearchCityPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.MyApp
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_city_post.*
import kotlinx.android.synthetic.main.layout_no_result.*

class CityPostFragment : BaseFragment<CityPostPresenter>(), CityPostView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    private var key: String? = null
    private var city: String? = null
    private var mSearchCityPostAdapter: SearchCityPostAdapter? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null

    companion object {
        fun getInstance(key: String, city: String): CityPostFragment {
            val cityPostFragment = CityPostFragment()
            val bundle = Bundle()
            bundle.putString(MyApp.KEY_CITY_POST, key)
            bundle.putString(MyApp.KEY_SEARCH_CITY, city)
            cityPostFragment.arguments = bundle
            return cityPostFragment
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_city_post

    override fun initView() {
        getDataArgument()
        setAdapter()
        setLinearLayoutManager()
        if (key == "driver") {
            fetchUserData(city)
        } else {
            fetchDriverData(city)
        }
    }

    private fun setLinearLayoutManager() {
        mLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = mLinearLayoutManager
    }

    private fun setAdapter() {
        mSearchCityPostAdapter = SearchCityPostAdapter(context!!, key!!)
        mSearchCityPostAdapter?.setLoadingMoreListener(this)
    }

    private fun fetchUserData(city: String?) {
        mPresenter!!.getUserCityPost(city!!)
    }

    private fun fetchDriverData(city: String?) {
        mPresenter!!.getDriverCityPost(city!!)
    }

    private fun getDataArgument() {
        val bundle = this.arguments
        key = bundle?.getString(MyApp.KEY_CITY_POST)
        city = bundle?.getString(MyApp.KEY_SEARCH_CITY)
    }

    override fun getPresenter(): CityPostPresenter {
        return CityPostPresenterImpl(this)
    }

    override fun showLoadingIndicatior() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        progress_bar.visibility = View.GONE
    }

    override fun showUserCityPost(data: List<UserSearchCityPostResponse>) {
        mSearchCityPostAdapter?.addModels(data, false)
    }

    override fun showDriverCityPost(data: List<DriverSearchCityPostResponse>) {
        mSearchCityPostAdapter?.addModels(data, false)
    }

    override fun showEmptyContent(check: Boolean) {
        if (check) {
            layout_no_result.visible()
        }else{
            layout_no_result.gone()
        }
    }

    override fun onLoadMore() {

    }


}