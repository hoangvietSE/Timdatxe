package com.example.anothertimdatxe.sprinthome.listrequest.driver

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.DriverListRequestAdapter
import com.example.anothertimdatxe.adapter.SpinnserStatus
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverListRequestResponse
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_driver_list_request.*

class DriverListRequestFragment : BaseFragment<DriverListRequestPresenter>(), DriverListRequestView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener {
    private var mDriverListRequestAdapter: DriverListRequestAdapter? = null
    private var mSpinnerAdapter: SpinnserStatus? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
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
    }

    private fun setAdapter() {
        mDriverListRequestAdapter = DriverListRequestAdapter(context!!)
    }

    private fun fetchListDriverBook() {
        mPresenter?.fetListDriverBook()
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
            }

        }
    }

    override fun onLoadMore() {

    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {

    }

    override fun hideLoadingItem() {
        mDriverListRequestAdapter?.hideLoadingItem()
    }

    override fun showLoadingItem() {
        mDriverListRequestAdapter?.showLoadingItem(true)
    }

    override fun enableLoadingMore(enable: Boolean) {
        mDriverListRequestAdapter?.enableLoadingMore(enable)
    }

    override fun setListItem(list: List<DriverListRequestResponse>) {
        mDriverListRequestAdapter?.addModels(list, false)
    }

    override fun showPreview() {
        showPreviewLoading()
    }

    override fun hidePreview() {
        hidePreviewLoading()
    }

}