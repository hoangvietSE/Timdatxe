package com.example.anothertimdatxe.presentation.map.mapsearch

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.MapSearchAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.common.BaseMapSearch
import com.example.anothertimdatxe.common.MapSearchListener
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
        const val STARTING_LOCATION_POINT_ID = "starting_location_point_id"
        const val ENDING_LOCATION_POINT_ID = "ending_location_point_id"
    }

    override val layoutRes: Int
        get() = R.layout.activity_map_search
    private var mBaseMapSearch: BaseMapSearch? = null
    private var mSearchLocation: MapSearchAdapter? = null
    private var mRole: String? = null
    private var mList: List<AutocompletePrediction>? = null
    private var mLocationStarting: MutableMap<String, String> = mutableMapOf()
    private var mLocationEnding: MutableMap<String, String> = mutableMapOf()
    private var mStartingPoint: String? = null
    private var mEndingPoint: String? = null

    override fun getPresenter(): MapSearchPresenter {
        return MapSearchPresenterImpl(this)
    }

    override fun initView() {
        setUpToolbar()
        getDataIntent()
        initAdapter()
        initBaseMapSearch()
    }

    private fun getDataIntent() {
        mStartingPoint = intent.getStringExtra(STARTING_LOCATION_POINT)
        mEndingPoint = intent.getStringExtra(ENDING_LOCATION_POINT)
        edt_starting_point.setText(mStartingPoint)
        edt_ending_point.setText(mEndingPoint)
    }

    override fun setListener() {
        btn_confirm.setOnClickListener {
            if (mLocationStarting.size > 0 || mLocationStarting.size > 0) {
                val intent = Intent()
                val keySetStarting = mLocationStarting.keys
                val keySetEnding = mLocationEnding.keys
                for (keyStarting in keySetStarting) {
                    intent.putExtra(STARTING_LOCATION_POINT_ID, keyStarting)
                    intent.putExtra(STARTING_LOCATION_POINT, mLocationStarting.get(keyStarting))
                }
                for (keyEnding in keySetEnding) {
                    intent.putExtra(ENDING_LOCATION_POINT_ID, keyEnding)
                    intent.putExtra(ENDING_LOCATION_POINT, mLocationEnding.get(keyEnding))
                }
                setResult(RESULT_CODE, intent)
                finish()
            } else {
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
                        mPresenter?.setStartingPointLocation(mList!![position].getFullText(null).toString(), mList!![position].placeId)
                    }
                    MapUtil.ROLE_MAP_SEARCH_ENDING_POINT -> {
                        mPresenter?.setEndingPointLocation(mList!![position].getFullText(null).toString(), mList!![position].placeId)
                    }
                }
            }

        })
        recycler_view.adapter = mSearchLocation
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun showLocationStarting(location: String, placeId: String) {
        hideLoading()
        edt_starting_point.setText(location)
        mLocationStarting?.clear()
        mLocationStarting?.put(placeId, location)
    }

    override fun showLocationEnding(location: String, placeId: String) {
        hideLoading()
        edt_ending_point.setText(location)
        mLocationEnding?.clear()
        mLocationEnding?.put(placeId, location)
    }
}