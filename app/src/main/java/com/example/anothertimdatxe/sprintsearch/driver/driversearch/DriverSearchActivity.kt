package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.DriverSearchUserPostAdapter
import com.example.anothertimdatxe.adapter.SpinnerSeatSearchAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.common.ItemRecyclerViewDecoration
import com.example.anothertimdatxe.entity.response.DriverSearchResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.request.DriverSearchRequest
import com.example.anothertimdatxe.sprinthome.listrequest.driver.detail.DriverRequestDetailActivity
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_driver_search.*

class DriverSearchActivity : BaseActivity<DriverSearchPresenter>(), DriverSearchView,
        DatePickerDialogWidget.onSetDateSuccessListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener {
    private var mList: MutableList<DriverSearchResponse>? = mutableListOf()
    private var mDriverSearchUserPostAdapter: DriverSearchUserPostAdapter? = null
    private var mSpinnerSeatSearchAdapter: SpinnerSeatSearchAdapter? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private val mSeatSearch: ArrayList<String> = arrayListOf("Chọn số ghế", "Từ 1 đến 10 ghế", "Từ 10 đến 20 ghế", "Lớn hơn 20 ghế")
    private var seatNumber: String = ""
    override val layoutRes: Int
        get() = R.layout.activity_driver_search

    override fun getPresenter(): DriverSearchPresenter {
        return DriverSearchPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        initSearSearch()
        setDateSearch()
        initAdapter()
        fetchData()
    }

    override fun setListener() {
        btn_search.setOnClickListener {
            val request = DriverSearchRequest()
            request.startingPoint = edt_starting_point.text.toString()
            request.endingPoint = edt_ending_point.text.toString()
            request.seatNumber = seatNumber
            request.date = tv_date.text.toString()?.let {
                if (!it.isNullOrEmpty() || !it.isNullOrBlank())
                    DateUtil.formatDate(it, DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
                else
                    ""
            }
            mPresenter!!.fetchDataSearch(request)
        }
    }

    private fun fetchData() {
        mPresenter!!.fetchData()
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.driver_search_btn).toUpperCase()
        }
    }

    private fun setDateSearch() {
        mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
        tv_date.setOnClickListener {
            mDatePickerDialogWidget!!.showDatePickerDialog()
        }
    }

    private fun initSearSearch() {
        mSpinnerSeatSearchAdapter = SpinnerSeatSearchAdapter(this, mSeatSearch)
        spinner.adapter = mSpinnerSeatSearchAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seatNumber = mSpinnerSeatSearchAdapter!!.getItem(position).toString()
            }

        }
    }

    private fun initAdapter() {
        mDriverSearchUserPostAdapter = DriverSearchUserPostAdapter(this)
        mDriverSearchUserPostAdapter?.setLoadingMoreListener(this)
        mDriverSearchUserPostAdapter?.addOnItemClickListener(this)
        recyclerView.addItemDecoration(ItemRecyclerViewDecoration(this, R.dimen.spacing_16_dp))
        recyclerView.adapter = mDriverSearchUserPostAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        tv_date.text = "${formatDateValue(dayOfMonth.toString())}/${formatDateValue(month.toString())}/${formatDateValue(year.toString())}"
        imv_close.visible()
        imv_close.setOnClickListener {
            tv_date.text = ""
            imv_close.gone()
        }
    }

    fun formatDateValue(value: String): String {
        if (value.length == 1) {
            return "0${value}"
        }
        return value
    }

    override fun enableLoadingMore(enable: Boolean) {
        mDriverSearchUserPostAdapter!!.enableLoadingMore(enable)
    }

    override fun showNoResult(check: Boolean) {
        if (check) {
            layout_no_result.visible()
        } else {
            layout_no_result.gone()
        }
    }

    override fun showListDriverSearch(data: List<DriverSearchResponse>) {
        hideLoadingMore()
        mDriverSearchUserPostAdapter!!.addModels(data, false)
        mList!!.addAll(data)
    }

    override fun onLoadMore() {
        showLoadingMore()
        mPresenter!!.fetchDataLoadMore()
    }

    override fun showLoadingMore() {
        mDriverSearchUserPostAdapter!!.showLoadingItem(true)
    }

    override fun hideLoadingMore() {
        mDriverSearchUserPostAdapter!!.hideLoadingItem()
    }

    override fun showListDriverSearchOnClick(data: List<DriverSearchResponse>) {
        mList!!.clear()
        mDriverSearchUserPostAdapter!!.clear()
        mList!!.addAll(data)
        mDriverSearchUserPostAdapter!!.addModels(data, false)
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {
        if(!avoidDoubleClick()){
            startActivity(Intent(this, DriverRequestDetailActivity::class.java).apply {
                putExtra(DriverRequestDetailActivity.USER_POST_ID, mList!![position].id)
            })
        }
    }
}