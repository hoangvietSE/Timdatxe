package com.example.anothertimdatxe.presentation.book.user.confirm

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.TimePicker
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerSeatAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.condition.ConditionActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.widget.TimePickerDialogWidget
import kotlinx.android.synthetic.main.activity_user_confirm_booking.*

class UserConfirmBookingActivity : BaseActivity<UserConfirmBookingPresenter>(), UserConfirmBookingView,
        DatePickerDialogWidget.onSetDateSuccessListener,
        TimePickerDialogWidget.onTimeSetListener {
    companion object {
        const val EXTRA_DRIVER_POST_ID = "extra_driver_post_id"
    }

    override val layoutRes: Int
        get() = R.layout.activity_user_confirm_booking
    private var driverPostId: Int = -1
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mTimePickerDialogWidget: TimePickerDialogWidget? = null
    private var mNumberSeatAdapter: SpinnerSeatAdapter? = null
    private var mUserConfirmBookingResponse: ConfirmBookingResponse? = null

    override fun getPresenter(): UserConfirmBookingPresenter {
        return UserConfirmBookingPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        getDataIntent()
        initDialog()
        initSpinner()
        fetchDataBooking()
    }

    private fun initSpinner() {
        mNumberSeatAdapter = SpinnerSeatAdapter(this, mutableListOf())
        sp_number_seat.adapter = mNumberSeatAdapter
        sp_number_seat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showPrice(position + 1)
            }

        }
    }

    override fun setListener() {
        tv_condition.setOnClickListener {
            startActivity(Intent(this, ConditionActivity::class.java))
        }
        tv_starting_date.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        tv_time.setOnClickListener {
            mTimePickerDialogWidget?.showTimePickerDialog()
        }
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.user_confirm_booking_toolbar_title).toUpperCase()
        }
    }

    private fun getDataIntent() {
        driverPostId = intent.getIntExtra(EXTRA_DRIVER_POST_ID, -1)
    }

    private fun initDialog() {
        mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
        mTimePickerDialogWidget = TimePickerDialogWidget(this, this)
    }

    private fun fetchDataBooking() {
        mPresenter?.fetchDataBooking(driverPostId)
    }

    override fun showDataBooking(data: ConfirmBookingResponse) {
        mUserConfirmBookingResponse = data
        val user = CarBookingSharePreference.getUserData()
        row_name.setDetail(user?.full_name ?: "")
        row_phone.setDetail(user?.phone ?: "")
        row_email.setDetail(user?.email ?: "")
        row_starting_date.setDetail(DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_19))
        row_time_estimate.setDetail(DateUtil.formatDate(data.endTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_19))
        tv_starting_point.text = data?.startPoint
        tv_ending_point.text = data?.endPoint
        tv_distance.text = NumberUtil.showDistance(data?.distance?.toString()!!)
        tv_percent.text = "100%"
        when (data?.type) {
            Constant.CONVENIENT_TRIP -> {
                tv_convinent.visible()
                tv_private.gone()
                row_one_seat.visible()
                rl_number_seat.visible()
                row_one_seat.setContent(NumberUtil.formatNumber(data?.regularPrice.toString()))
                showPrice(data?.emptySeat!!)
            }
            Constant.PRIVATE_TRIP -> {
                tv_convinent.gone()
                tv_private.visible()
                row_one_seat.gone()
                rl_number_seat.gone()
                row_total_money.setContent(NumberUtil.formatNumber(data?.privatePrice2.toString()))
            }
        }
        edt_number_seat.setText(data?.emptySeat?.toString())
        mPresenter?.setSeatSpinner(data?.emptySeat)
    }

    private fun showPrice(seat: Int) {
        edt_number_seat.setText(seat.toString())
        val totalMoney = mUserConfirmBookingResponse?.regularPrice?.toInt()?.times(seat)
        row_total_money.setContent(NumberUtil.formatNumber(totalMoney?.toString()!!))
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        tv_starting_date.text = "${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}/${DateUtil.formatValue(year.toString())}"
    }

    override fun onSetTimeSuccess(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tv_time.text = "${DateUtil.formatValue(hourOfDay.toString())}:${DateUtil.formatValue(minute.toString())}"
    }

    override fun addSeatToSpinner(data: MutableList<String>) {
        mNumberSeatAdapter?.addAll(data)
    }
}