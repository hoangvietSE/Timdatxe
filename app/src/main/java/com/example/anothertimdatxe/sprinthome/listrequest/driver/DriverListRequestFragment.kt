package com.example.anothertimdatxe.sprinthome.listrequest.driver

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.DriverListRequestAdapter
import com.example.anothertimdatxe.adapter.SpinnserStatus
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverListRequestResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.listrequest.driver.detail.DriverRequestDetailActivity
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_driver_list_request.*
import kotlinx.android.synthetic.main.layout_no_result.*

class DriverListRequestFragment : BaseFragment<DriverListRequestPresenter>(), DriverListRequestView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener {
    private var mDriverListRequestAdapter: DriverListRequestAdapter? = null
    private var mSpinnerAdapter: SpinnserStatus? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var isLoading = false
    private var enable = false
    override val layoutRes: Int
        get() = R.layout.fragment_driver_list_request

    companion object {
        fun getInstance(): DriverListRequestFragment {
            val fragment = DriverListRequestFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getPresenter(): DriverListRequestPresenter {
        return DriverListRequestPresenterImpl(this)
    }

    override fun initView() {
        loadBanner()
        initSpinner()
        setDatePicker()
        setAdapter()
        fetchListDriverBook()
        setRefresh()
    }

    override fun initListener() {
        tv_date.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
    }

    private fun setRefresh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }
        swipeRefresh.setOnRefreshListener {
            clearData()
            fetchListDriverBook()
        }
    }

    private fun setAdapter() {
        mDriverListRequestAdapter = DriverListRequestAdapter(context!!)
        mDriverListRequestAdapter?.setLoadingMoreListener(this)
        mDriverListRequestAdapter?.addOnItemClickListener(this)
        recyclerView.adapter = mDriverListRequestAdapter
        val layoutManager = object : LinearLayoutManager(activity){
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        recyclerView.layoutManager = layoutManager
        nestedScroolView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v?.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) && (scrollY > oldScrollY)) {
                    if (isLoading || !enable) {
                        return@setOnScrollChangeListener
                    }
                    val visibleItemCount = recyclerView.layoutManager?.childCount
                    val totalItemCount = recyclerView.layoutManager?.itemCount
                    val pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (pastVisiblesItems + visibleItemCount!! >= totalItemCount!!) {
                        if (!isLoading) {
                            isLoading = true
                            onLoadMore()
                        }
                    }
                }
            }
        }
    }

    private fun fetchListDriverBook() {
        mPresenter?.refreshList()
    }

    private fun loadBanner() {
        GlideApp.with(this)
                .load(R.drawable.banner_driver_list_request)
                .into(imv_banner)
    }

    private fun setDatePicker() {
        mDatePickerDialogWidget = DatePickerDialogWidget(context!!, object : DatePickerDialogWidget.onSetDateSuccessListener {
            override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
                tv_date.setText(
                        "${DateUtil.formatValue(dayOfMonth.toString())}/" +
                                "${DateUtil.formatValue(month.toString())}/" +
                                "${DateUtil.formatValue(year.toString())}")
                imv_close.visible()
                imv_close.setOnClickListener {
                    tv_date.setText("")
                    imv_close.gone()
                    fetchData(tv_date.text.toString())
                }
                fetchData(tv_date.text.toString())
            }
        })
    }

    private fun initSpinner() {
        mPresenter!!.setSpinnerStatus()
    }

    override fun setSpinnerStatus(list: List<String>) {
        mSpinnerAdapter = SpinnserStatus(context!!, list)
        sp_status.adapter = mSpinnerAdapter
        sp_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fetchData(position)
            }

        }

    }

    override fun onLoadMore() {
        nestedScroolView.post {
            nestedScroolView.smoothScrollTo(0, nestedScroolView.bottom)
            nestedScroolView.fullScroll(View.FOCUS_DOWN)
        }
        showLoadingItem()
        mDriverListRequestAdapter?.setIsLoading(true)
        mPresenter?.fetListDriverBook()
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {
        if (!avoidDoubleClick()) {
            startActivity(Intent(context!!, DriverRequestDetailActivity::class.java).apply {
                putExtra(DriverRequestDetailActivity.USER_POST_ID, mDriverListRequestAdapter?.getItem(position, DriverListRequestResponse::class.java)?.user_post_id)
            })
        }
    }

    override fun hideLoadingItem() {
        mDriverListRequestAdapter?.hideLoadingItem()
    }

    override fun showLoadingItem() {
        mDriverListRequestAdapter?.showLoadingItem(true)
    }

    override fun enableLoadingMore(enable: Boolean) {
        mDriverListRequestAdapter?.enableLoadingMore(enable)
        this.enable = enable
    }

    override fun setListItem(list: List<DriverListRequestResponse>) {
        isLoading = false
        enableRefreshLoading(false)
        hideLoadingItem()
        mDriverListRequestAdapter?.addModels(list, false)
    }

    override fun showPreview() {
        showPreviewLoading()
    }

    override fun hidePreview() {
        hidePreviewLoading()
    }

    fun enableRefreshLoading(enable: Boolean) {
        if (enable) {
            swipeRefresh.isRefreshing = true
        } else {
            swipeRefresh.isRefreshing = false
        }
    }

    fun clearData() {
        mDriverListRequestAdapter?.clear()
    }

    override fun showNoResult(check: Boolean) {
        if (check) {
            layout_no_result.visible()
        } else {
            layout_no_result.gone()
        }
    }

    fun fetchData(date: String?) {
        clearData()
        enableRefreshLoading(true)
        mPresenter?.fetchListDriverBook(date ?: "")
    }

    fun fetchData(status: Int) {
        clearData()
        enableRefreshLoading(true)
        mPresenter?.fetchListDriverBook(if (status == 0) null else status - 1)
    }

}