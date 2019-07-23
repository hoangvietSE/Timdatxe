package com.example.anothertimdatxe.sprinthome.hotcities

import androidx.recyclerview.widget.GridLayoutManager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.HotCitiesAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import kotlinx.android.synthetic.main.activity_hot_cities.*

class HotCitiesActivity : BaseActivity<HotCitiesPresenter>(), HotCitiesView, BaseRvListener {
    private var mListHotCities: List<HotCitiesResponse>? = null
    private var mHotCitiesAdapter: HotCitiesAdapter? = null
    override fun onItemClick(position: Int) {

    }

    companion object {
        const val HOT_CITIES = "hot_cities"
    }

    override val layoutRes: Int
        get() = R.layout.activity_hot_cities

    override fun getPresenter(): HotCitiesPresenter {
        return HotCitiesPresenterImpl(this)
    }

    override fun initView() {
        getDataIntent()
        setAdapter()
        initListHotCities()
    }

    private fun setAdapter() {
        mHotCitiesAdapter = HotCitiesAdapter(this, this)
        mHotCitiesAdapter?.let {
            it.setListItems(mListHotCities!!)
        }
    }

    private fun initListHotCities() {
        recyclerView.adapter = mHotCitiesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getDataIntent() {
        mListHotCities = intent.extras.getParcelableArrayList(HOT_CITIES)
    }
}