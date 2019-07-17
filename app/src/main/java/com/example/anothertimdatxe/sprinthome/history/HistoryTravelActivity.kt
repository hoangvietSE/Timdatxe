package com.example.anothertimdatxe.sprinthome.history

import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.DriverHistoryAdapter
import com.example.anothertimdatxe.adapter.UserHistoryAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.DriverHistoryResponse
import com.example.anothertimdatxe.entity.response.UserHistoryResponse
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_history_travel.*

class HistoryTravelActivity : BaseActivity<HistoryTravelPresenter>(), HistoryTravelView,
        RecyclerViewAdapter.OnItemClickListener {
    private var isUserHistory: Boolean = true
    private var mUserHistoryAdapter: UserHistoryAdapter? = null
    private var mDriverHistoryAdapter: DriverHistoryAdapter? = null

    companion object {
        const val HISTORY_TRAVEL = "history_travel"
    }

    override val layoutRes: Int
        get() = R.layout.activity_history_travel

    override fun getPresenter(): HistoryTravelPresenter {
        return HistoryTravelPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        getDataIntent()
        setAdapter()
        getHistoryTravel()
    }

    private fun setAdapter() {
        if (isUserHistory) {
            mUserHistoryAdapter = UserHistoryAdapter(this, false)
            mUserHistoryAdapter!!.addOnItemClickListener(this)
            recyclerView.adapter = mUserHistoryAdapter
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        } else {
            mDriverHistoryAdapter = DriverHistoryAdapter(this, false)
            mDriverHistoryAdapter!!.addOnItemClickListener(this)
            recyclerView.adapter = mDriverHistoryAdapter
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
    }

    private fun getHistoryTravel() {
        mPresenter!!.getData(isUserHistory)
    }

    private fun getDataIntent() {
        isUserHistory = intent.getBooleanExtra(HISTORY_TRAVEL, true)
    }

    private fun setToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar!!.setBackgroundColor(resources.getColor(R.color.colorPrimary, null))
        } else {
            toolbar!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }
        toolbarTitle?.let {
            it.text = resources.getString(R.string.history_travel_title).toUpperCase()
        }
    }

    override fun showUserHistory(data: List<UserHistoryResponse>) {
        mUserHistoryAdapter!!.addModels(data, false)
    }

    override fun showDriverHistory(data: List<DriverHistoryResponse>) {
        mDriverHistoryAdapter!!.addModels(data, false)
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {
    }

    override fun setNumberTrip(count: Int) {
        tv_number_trip.text = count.toString()
    }
}