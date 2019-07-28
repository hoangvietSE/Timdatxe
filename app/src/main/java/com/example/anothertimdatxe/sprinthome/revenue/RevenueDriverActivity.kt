package com.example.anothertimdatxe.sprinthome.revenue

import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.RevenueDetailAdapter
import com.example.anothertimdatxe.adapter.RevenueMonthAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.entity.response.DriverRevenueResponse
import com.example.anothertimdatxe.sprinthome.revenue.item.ItemMonth
import com.example.anothertimdatxe.sprinthome.revenue.item.ItemSpacingDecoration
import com.example.anothertimdatxe.util.NumberUtil
import com.example.anothertimdatxe.widget.BaseRecyclerView
import com.example.anothertimdatxe.widget.CustomLinearSnapHelper
import kotlinx.android.synthetic.main.activity_revenue_driver.*
import java.util.*

class RevenueDriverActivity : BaseActivity<RevenueDriverPresenter>(), RevenueDriverView, BaseRvListener, BaseRecyclerView.onRefreshLoadMoreListener {
    private var mListMonth: MutableList<ItemMonth> = mutableListOf()
    private var mListRevenueDetail: MutableList<DriverRevenueResponse> = mutableListOf()
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mCustomLinearLayoutManager: CustomLinearSnapHelper? = null
    private var mRevenueMonthAdapter: RevenueMonthAdapter? = null
    private var mRevenueDetailAdapter: RevenueDetailAdapter? = null
    private var mCurrentMonth: Int = 0
    override val layoutRes: Int
        get() = R.layout.activity_revenue_driver

    override fun getPresenter(): RevenueDriverPresenter {
        return RevenueDriverPresenterImpl(this)
    }

    private val mMonthListener = object : BaseRvListener {
        override fun onItemClick(position: Int) {
            setItemPosition(position)
            mPresenter!!.fetchData(position + 1, false)
        }
    }

    override fun onItemClick(position: Int) {
        //Detail Revenue OnClick
    }

    fun setItemPosition(position: Int) {
        mCurrentMonth = position + 1
        mListMonth[position].isSelected = true
        mListMonth.forEach {
            if (it != mListMonth[position]) {
                it.isSelected = false
            }
        }
        mRevenueMonthAdapter!!.notifyDataSetChanged()
        scroolToPosition(position)
    }

    private fun scroolToPosition(position: Int) {
        var firstVisibleItemPosition = mLinearLayoutManager!!.findFirstVisibleItemPosition()
        var lastVisibleItemPosition = mLinearLayoutManager!!.findLastVisibleItemPosition()
        var centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        if (position == 0 || position == mRevenueMonthAdapter!!.getListItem().size - 1){
            recyclerViewMonth.smoothScrollToPosition(position)
        }else{
            if (position > centerPosition) {
                recyclerViewMonth.smoothScrollToPosition(position + 1)
            } else {
                recyclerViewMonth.smoothScrollToPosition(position - 1)
            }
        }
    }

    override fun initView() {
        setLayoutToolbar()
        initRevenueDetailAdapter()
        initListMonth()
        setLinearSnapHelper()
        setLayoutManager()
        setMonthAdapter()
        fetchDateCurrentTime()
    }

    private fun setLayoutManager() {
        mLinearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewMonth.layoutManager = mLinearLayoutManager
    }

    private fun setLinearSnapHelper() {
        mCustomLinearLayoutManager = CustomLinearSnapHelper()
        mCustomLinearLayoutManager!!.attachToRecyclerView(recyclerViewMonth)
    }

    private fun fetchDateCurrentTime() {
        val month = Calendar.getInstance().get(Calendar.MONTH)
        mCurrentMonth = month
        setItemPosition(month)
        scroolToPosition(month)
        mPresenter!!.fetchData(month + 1, false)
    }

    private fun setLayoutToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar!!.background = resources.getDrawable(R.color.colorPrimary, null)
        } else {
            toolbar!!.background = resources.getDrawable(R.color.colorPrimary)
        }
        toolbarTitle?.let {
            it.text = resources.getString(R.string.revenue_toolbar_title).toUpperCase()
        }
        leftbutton?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp, null))
            } else {
                it.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp))
            }
        }
    }

    private fun initListMonth() {
        for (i in 1..12) {
            mListMonth.add(ItemMonth("Tháng $i", false))
        }
    }

    private fun setMonthAdapter() {
        mRevenueMonthAdapter = RevenueMonthAdapter(this, mMonthListener)
        mRevenueMonthAdapter?.setListItems(mListMonth)
        val spacingDecoration = resources.getDimensionPixelSize(R.dimen.spacing_16_dp)
        recyclerViewMonth.addItemDecoration(ItemSpacingDecoration(spacingDecoration))
        recyclerViewMonth.adapter = mRevenueMonthAdapter
//        recyclerViewMonth.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun initRevenueDetailAdapter() {
        mRevenueDetailAdapter = RevenueDetailAdapter(this, this)
    }

    override fun showDataByMonth(data: List<DriverRevenueResponse>, isRefreshing: Boolean) {
        mListRevenueDetail.clear()
        mListRevenueDetail.addAll(data)
        mRevenueDetailAdapter?.setListItems(mListRevenueDetail)
        recyclerViewDetailMonth.setAdapter(mRevenueDetailAdapter!!)
        recyclerViewDetailMonth.setOnRefreshAndLoadMore(this)
        recyclerViewDetailMonth.setListLayoutManager()
        if (isRefreshing) {
            recyclerViewDetailMonth.hideLoading()
        }
    }

    override fun showDetail(sum_trip: Int, sum_revenue: Int) {
        row_sum_revenue.setContent(NumberUtil.formatNumber(sum_revenue.toString()))
        row_sum_trip.setContent("$sum_trip chuyến")
    }

    override fun onRefresh() {
        mPresenter!!.fetchData(mCurrentMonth, true)
    }

    override fun onLoadMore() {

    }
}