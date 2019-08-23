package com.example.anothertimdatxe.presentation.map.mapsearch

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.BaseMapSearch
import com.example.anothertimdatxe.adapter.MapSearchAdapter
import com.example.anothertimdatxe.adapter.MapSearchListener
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.util.MapUtil
import com.example.anothertimdatxe.widget.MapSearchTextWatcher
import com.google.android.libraries.places.api.model.AutocompletePrediction
import kotlinx.android.synthetic.main.activity_map_search.*

class MapSearchActivity : BaseActivity<MapSearchPresenter>(), MapSearchView {
    companion object {
        val TAG = MapSearchActivity::class.java.simpleName
        const val RESULT_CODE = 1998
        const val STARTING_LOCATION_POINT = "starting_location_point"
        const val ENDING_LOCATION_POINT = "ending_location_point"
    }

    override val layoutRes: Int
        get() = R.layout.activity_map_search
    private var mBaseMapSearch: BaseMapSearch? = null
    private var mSearchLocation: MapSearchAdapter? = null
    private var mRole: String? = null
    private var mList: List<AutocompletePrediction>? = null
    private var mLocationStartingPoint: ArrayList<String> = arrayListOf()
    private var mLocationEndingPoint: ArrayList<String> = arrayListOf()

    override fun getPresenter(): MapSearchPresenter {
        return MapSearchPresenterImpl(this)
    }

    override fun initView() {
        setUpToolbar()
        initAdapter()
        initBaseMapSearch()
    }

    override fun setListener() {
        btn_confirm.setOnClickListener {
            if (mLocationStartingPoint.size > 0 || mLocationEndingPoint.size > 0) {
                val intent = Intent()
                intent.putExtra(STARTING_LOCATION_POINT, mLocationStartingPoint[0])
                intent.putExtra(ENDING_LOCATION_POINT, mLocationEndingPoint[0])
                setResult(RESULT_CODE, intent)
                finish()
            }
        }
    }

    private fun initBaseMapSearch() {
        mBaseMapSearch = BaseMapSearch(this, object : MapSearchListener {
            override fun onMapSearchSuccess(list: List<AutocompletePrediction>, role: String) {
                mList = list
                mRole = role
                when (role) {
                    MapUtil.ROLE_MAP_SEARCH_STARTING_POINT -> {
                        mSearchLocation?.setListItems(mList!!)
                    }
                    MapUtil.ROLE_MAP_SEARCH_ENDING_POINT -> {
                        mSearchLocation?.setListItems(mList!!)
                    }
                }
            }

            override fun onMapSearchFail(exception: Exception) {
                Log.d(TAG, "Base Map Search: Exception")
            }
        })
        edt_starting_point.addTextChangedListener(MapSearchTextWatcher(mBaseMapSearch!!, MapUtil.ROLE_MAP_SEARCH_STARTING_POINT))
        edt_ending_point.addTextChangedListener(MapSearchTextWatcher(mBaseMapSearch!!, MapUtil.ROLE_MAP_SEARCH_ENDING_POINT))
    }

    private fun setUpToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.map_search_starting_point)
        }
    }

    private fun initAdapter() {
        mSearchLocation = MapSearchAdapter(this, object : BaseRvListener {
            override fun onItemClick(position: Int) {
                when (mRole) {
                    MapUtil.ROLE_MAP_SEARCH_STARTING_POINT -> {
                        mPresenter?.setStartingPointLocation(mList!![position].getFullText(null).toString())
                    }
                    MapUtil.ROLE_MAP_SEARCH_ENDING_POINT -> {
                        mPresenter?.setEndingPointLocation(mList!![position].getFullText(null).toString())
                    }
                }
            }

        })
        recycler_view.adapter = mSearchLocation
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun showLocationStarting(location: String) {
        hideLoading()
        edt_starting_point.setText(location)
        mLocationStartingPoint.clear()
        mLocationStartingPoint.add(location)
    }

    override fun showLocationEnding(location: String) {
        hideLoading()
        edt_ending_point.setText(location)
        mLocationEndingPoint.clear()
        mLocationEndingPoint.add(location)

    }
}