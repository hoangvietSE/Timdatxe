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
import kotlinx.android.synthetic.main.activity_revenue_driver.*
import java.util.*

class RevenueDriverActivity : BaseActivity<RevenueDriverPresenter>(), RevenueDriverView, BaseRvListener, BaseRecyclerView.onRefreshLoadMoreListener {
    private var mListMonth: MutableList<ItemMonth> = mutableListOf()
    private var mListRevenueDetail: MutableList<DriverRevenueResponse> = mutableListOf()
    private var mRevenueMonthAdapter: RevenueMonthAdapter? = null
    private var mRevenueDetailAdapter: RevenueDetailAdapter? = null
    override val layoutRes: Int
        get() = R.layout.activity_revenue_driver

    override fun getPresenter(): RevenueDriverPresenter {
        return RevenueDriverPresenterImpl(this)
    }

    override fun onItemClick(position: Int) {
        setItemPosition(position)
        mPresenter!!.fetchData((position + 1))
    }

    fun setItemPosition(position: Int) {
        mListMonth[position].isSelected = true
        mListMonth.forEach {
            if (it != mListMonth[position]) {
                it.isSelected = false
            }
        }
        mRevenueMonthAdapter!!.notifyDataSetChanged()
    }

    override fun initView() {
        setLayoutToolbar()
        initRevenueDetailAdapter()
        initListMonth()
        setMonthAdapter()
        fetchDateCurrentTime()
    }

    private fun fetchDateCurrentTime() {
        val month = Calendar.getInstance().get(Calendar.MONTH)
        setItemPosition(month)
        recyclerViewMonth.smoothScrollToPosition(month)
        mPresenter!!.fetchData(month + 1)
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
        mRevenueMonthAdapter = RevenueMonthAdapter(this, this)
        mRevenueMonthAdapter?.setListItems(mListMonth)
        val spacingDecoration = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerViewMonth.addItemDecoration(ItemSpacingDecoration(spacingDecoration))
        recyclerViewMonth.adapter = mRevenueMonthAdapter
        recyclerViewMonth.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun initRevenueDetailAdapter() {
        mRevenueDetailAdapter = RevenueDetailAdapter(this, this)
    }

    override fun showDataByMonth(data: List<DriverRevenueResponse>) {
        mListRevenueDetail.clear()
        mListRevenueDetail.addAll(data)
        mRevenueDetailAdapter?.setListItems(mListRevenueDetail)
        recyclerViewDetailMonth.setAdapter(mRevenueDetailAdapter!!)
        recyclerViewDetailMonth.setOnRefreshAndLoadMore(this)
        recyclerViewDetailMonth.setListLayoutManager()
    }

    override fun showDetail(sum_trip: Int, sum_revenue: Int) {
        row_sum_revenue.setContent(NumberUtil.formatNumber(sum_revenue.toString()))
        row_sum_trip.setContent("$sum_trip chuyến")
    }

    override fun onRefresh() {

    }

    override fun onLoadMore() {

    }
}