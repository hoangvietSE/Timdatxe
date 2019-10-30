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
import com.example.anothertimdatxe.request.TimeBookingRequest
import com.example.anothertimdatxe.request.UserBookingRequest
import com.example.anothertimdatxe.sprinthome.condition.ConditionActivity
import com.example.anothertimdatxe.util.*
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
        tv_condition_one.setOnClickListener {
            cb_condition.isChecked = true
        }
        tv_condition_two.setOnClickListener {
            cb_condition.isChecked = true
            startActivity(Intent(this, ConditionActivity::class.java))
        }
        tv_starting_date.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        tv_time.setOnClickListener {
            mTimePickerDialogWidget?.showTimePickerDialog()
        }
        btn_payment.setOnClickListener {
            if(onCheckedCondition()){
                payment()
            }else{
                ToastUtil.show(resources.getString(R.string.user_confirm_booking_not_check_condition))
            }
        }
    }

    private fun payment() {
        val userBookingRequest = UserBookingRequest()
        val timeBookingRequest = TimeBookingRequest()
        userBookingRequest.distance = mUserConfirmBookingResponse?.distance
        userBookingRequest.driverId = mUserConfirmBookingResponse?.driverId
        userBookingRequest.driverPostId = mUserConfirmBookingResponse?.id
        userBookingRequest.email = row_email.getDetail()
        userBookingRequest.phone = row_phone.getDetail()
        userBookingRequest.fullName = row_name.getDetail()
        userBookingRequest.emptySeat = mUserConfirmBookingResponse?.emptySeat
        userBookingRequest.startPoint = tv_starting_point.text.toString()
        userBookingRequest.endPoint = tv_ending_point.text.toString()
        userBookingRequest.latFrom = mUserConfirmBookingResponse?.latFrom
        userBookingRequest.lngFrom = mUserConfirmBookingResponse?.lngFrom
        userBookingRequest.latTo = mUserConfirmBookingResponse?.latTo
        userBookingRequest.lngTo = mUserConfirmBookingResponse?.lngTo
        userBookingRequest.note = mUserConfirmBookingResponse?.description ?: ""
        userBookingRequest.numberSeat = edt_number_seat.text.toString().toInt()
        userBookingRequest.type = mUserConfirmBookingResponse?.type
        if (mUserConfirmBookingResponse?.type == Constant.CONVENIENT_TRIP) {
            userBookingRequest.price = mUserConfirmBookingResponse?.regularPrice
            userBookingRequest.totalPrice = mUserConfirmBookingResponse?.regularPrice?.toInt()?.times(edt_number_seat.text.toString().toInt())?.toString()
        } else {
            userBookingRequest.totalPrice = mUserConfirmBookingResponse?.privatePrice2
        }
        userBookingRequest.userId = CarBookingSharePreference.getUserId()
        userBookingRequest?.waypoints = ""
        userBookingRequest?.canBook = 0
        timeBookingRequest.bookDate = tv_starting_date.text.toString()
        timeBookingRequest.bookHour = tv_time.text.toString()
        timeBookingRequest.startTime = mUserConfirmBookingResponse?.startTime
        timeBookingRequest.endTime = mUserConfirmBookingResponse?.endTime
        clearAllRequestFocus()
        mPresenter?.paymentBooking(userBookingRequest,timeBookingRequest)
    }

    private fun clearAllRequestFocus() {
        row_name.clearError()
        row_phone.clearError()
        row_email.clearError()
        tv_starting_date.error = null
        tv_time.error = null
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

    override fun onNameEmpty() {
        row_name.requestFocus(resources.getString(R.string.user_confirm_booking_name_empty))
    }

    override fun onNameError() {
        row_name.requestFocus(resources.getString(R.string.user_confirm_booking_name_error))
    }

    override fun onPhoneEmpty() {
        row_phone.requestFocus(resources.getString(R.string.user_confirm_booking_phone_empty))
    }

    override fun onPhoneError() {
        row_phone.requestFocus(resources.getString(R.string.user_confirm_booking_phone_error))
    }

    override fun onEmailError() {
        row_email.requestFocus(resources.getString(R.string.user_confirm_booking_email_empty))
    }

    override fun onEmailEmpty() {
        row_email.requestFocus(resources.getString(R.string.user_confirm_booking_email_error))
    }

    override fun onStartingPointEmpty() {

    }

    override fun onEndingPointEmpty() {

    }

    override fun onDateEmpty() {
        onDateRequestFocus(resources.getString(R.string.user_confirm_booking_date_empty))
    }

    override fun onDateInPast() {
        onDateRequestFocus(resources.getString(R.string.user_confirm_booking_date_in_past))
    }

    override fun onDateBeforeStartingTime() {
        onDateRequestFocus(resources.getString(R.string.user_confirm_booking_date_before_start_time))
    }

    override fun onDateAfterEndingTime() {
        onDateRequestFocus(resources.getString(R.string.user_confirm_booking_date_after_end_time))
    }

    override fun onHourEmpty() {
        onHourRequestFocus(resources.getString(R.string.user_confirm_booking_hour_empty))
    }

    override fun onHourBeforeStartingTime() {
        onHourRequestFocus(resources.getString(R.string.user_confirm_booking_hour_before_start_time))
    }

    override fun onHourAfterEndingTime() {
        onHourRequestFocus(resources.getString(R.string.user_confirm_booking_hour_after_end_time))
    }

    private fun onDateRequestFocus(error:String){
        tv_starting_date.error = error
        tv_starting_date.requestFocus()
    }

    private fun onHourRequestFocus(error:String){
        tv_time.error = error
        tv_time.requestFocus()
    }

    private fun onCheckedCondition(): Boolean {
        return cb_condition.isChecked
    }
}