package com.example.anothertimdatxe.sprintsearch.driver.driversearch

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerSeatSearchAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import kotlinx.android.synthetic.main.activity_driver_search.*

class DriverSearchActivity : BaseActivity<DriverSearchPresenter>(), DriverSearchView, DatePickerDialogWidget.onSetDateSuccessListener {
    private var mSpinnerSeatSearchAdapter: SpinnerSeatSearchAdapter? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private val mSeatSearch: ArrayList<String> = arrayListOf("Chọn số ghế", "Từ 1 đến 10 ghế", "Từ 10 đến 20 ghế", "Lớn hơn 20 ghế")
    override val layoutRes: Int
        get() = R.layout.activity_driver_search

    override fun getPresenter(): DriverSearchPresenter {
        return DriverSearchPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        initSearSearch()
        setDateSearch()
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
}