package com.example.anothertimdatxe.sprinthome.hotcities

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.HotCitiesAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.sprinthome.city_post.CityPostActivity
import com.example.anothertimdatxe.sprinthome.hotcities.decoration.SpacingItemDecoration
import kotlinx.android.synthetic.main.activity_hot_cities.*

class HotCitiesActivity : BaseActivity<HotCitiesPresenter>(), HotCitiesView, BaseRvListener {
    private var mListHotCities: ArrayList<HotCitiesResponse>? = null
    private var mHotCitiesAdapter: HotCitiesAdapter? = null
    private var mSpacing: Int? = null
    override fun onItemClick(position: Int) {
        startActivity(Intent(this@HotCitiesActivity, CityPostActivity::class.java).apply {
            putExtra(CityPostActivity.BANNER_CITY_POST, mListHotCities!![position]?.app_image)
            putExtra(CityPostActivity.CITY_POST, mListHotCities!![position]?.name)
        })
    }

    companion object {
        const val HOT_CITIES = "hot_cities"
        const val SPAN_COUNT = 2
    }

    override val layoutRes: Int
        get() = R.layout.activity_hot_cities

    override fun getPresenter(): HotCitiesPresenter {
        return HotCitiesPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        getDataIntent()
        setAdapter()
        initListHotCities()
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.hot_cities_title)
        }
    }

    private fun setAdapter() {
        mHotCitiesAdapter = HotCitiesAdapter(this, this)
        mHotCitiesAdapter?.let {
            it.setListItems(mListHotCities!!)
        }
    }

    private fun initListHotCities() {
        recyclerView.adapter = mHotCitiesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        mSpacing = resources.getDimensionPixelSize(R.dimen.spacing_8_dp)
        recyclerView.addItemDecoration(SpacingItemDecoration(mSpacing!!))
    }

    private fun getDataIntent() {
        mListHotCities = intent.extras.getParcelableArrayList(HOT_CITIES)
    }
}