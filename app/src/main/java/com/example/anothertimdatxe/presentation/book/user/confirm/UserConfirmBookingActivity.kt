package com.example.anothertimdatxe.presentation.book.user.confirm

import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.TimePicker
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerSeatAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.WrapperItem
import com.example.anothertimdatxe.entity.response.confirmbooking.ConfirmBookingResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.map.entity.Route
import com.example.anothertimdatxe.presentation.map.mapsearch.MapSearchActivity
import com.example.anothertimdatxe.request.TimeBookingRequest
import com.example.anothertimdatxe.request.UserBookingRequest
import com.example.anothertimdatxe.sprinthome.settings.condition.ConditionActivity
import com.example.anothertimdatxe.util.*
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.widget.TimePickerDialogWidget
import kotlinx.android.synthetic.main.activity_user_confirm_booking.*


class UserConfirmBookingActivity : BaseActivity<UserConfirmBookingPresenter>(), UserConfirmBookingView,
        DatePickerDialogWidget.OnSetDateSuccessListener,
        TimePickerDialogWidget.OnTimeSetListener {
    companion object {
        const val EXTRA_DRIVER_POST_ID = "extra_driver_post_id"
        const val REQUEST_CODE_MAP_SEARCH = 9009
    }

    override val layoutRes: Int
        get() = R.layout.activity_user_confirm_booking
    private var driverPostId: Int = -1
        get() = intent.getIntExtra(EXTRA_DRIVER_POST_ID, -1)
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mTimePickerDialogWidget: TimePickerDialogWidget? = null
    private var mNumberSeatAdapter: SpinnerSeatAdapter? = null
    private var mLocationStartingPointId: String? = null
    private var mLocationEndingPointId: String? = null
    private var mLocationStartingPoint: String? = null
    private var mLocationEndingPoint: String? = null
    private var listItem: MutableList<WrapperItem> = mutableListOf()

    override fun getPresenter(): UserConfirmBookingPresenter {
        return UserConfirmBookingPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
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
                edt_number_seat.setText((position + 1).toString())
                mPresenter?.setNumberSeat(position + 1)
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
        tv_convinent.setOnClickListener {
            onConvenientTrip(true)
        }
        tv_private.setOnClickListener {
            onPrivateTrip(true)
        }
        tv_starting_point.setOnClickListener {
            startActivityForResult(Intent(this, MapSearchActivity::class.java).apply {
                putExtra(MapSearchActivity.STARTING_LOCATION_POINT, tv_starting_point.text.toString())
                putExtra(MapSearchActivity.ENDING_LOCATION_POINT, tv_ending_point.text.toString())
            }, REQUEST_CODE_MAP_SEARCH)
        }
        tv_ending_point.setOnClickListener {
            startActivityForResult(Intent(this, MapSearchActivity::class.java).apply {
                putExtra(MapSearchActivity.STARTING_LOCATION_POINT, tv_starting_point.text.toString())
                putExtra(MapSearchActivity.ENDING_LOCATION_POINT, tv_ending_point.text.toString())
            }, REQUEST_CODE_MAP_SEARCH)
        }
        btn_payment.setOnClickListener {
            if (onCheckedCondition()) {
                payment()
            } else {
                ToastUtil.show(resources.getString(R.string.user_confirm_booking_not_check_condition))
            }
        }
    }

    private fun payment() {
        val userBookingRequest = UserBookingRequest()
        val timeBookingRequest = TimeBookingRequest()
        userBookingRequest.email = row_email.getDetail()
        userBookingRequest.phone = row_phone.getDetail()
        userBookingRequest.fullName = row_name.getDetail()
        userBookingRequest.startPoint = tv_starting_point.text.toString()
        userBookingRequest.endPoint = tv_ending_point.text.toString()
        userBookingRequest.numberSeat = edt_number_seat.text.toString().toInt()
        userBookingRequest.userId = CarBookingSharePreference.getUserId()
        userBookingRequest.waypoints = ""
        userBookingRequest.canBook = 0
        timeBookingRequest.bookDate = tv_starting_date.text.toString()
        timeBookingRequest.bookHour = tv_time.text.toString()
        clearAllRequestFocus()
        mPresenter?.paymentBooking(userBookingRequest, timeBookingRequest)
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

    private fun initDialog() {
        mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
        mTimePickerDialogWidget = TimePickerDialogWidget(this, this)
    }

    private fun fetchDataBooking() {
        mPresenter?.fetchDataBooking(driverPostId)
    }

    override fun showDataBooking(data: ConfirmBookingResponse) {
        val user = CarBookingSharePreference.getUserData()
        row_name.setDetail(user?.fullName ?: "")
        row_phone.setDetail(user?.phone ?: "")
        row_email.setDetail(user?.email ?: "")
        row_starting_date.setDetail(DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_19))
        row_time_estimate.setDetail(DateUtil.formatDate(data.endTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_19))
        tv_starting_point.text = data.startPoint
        tv_ending_point.text = data.endPoint
        tv_distance.text = NumberUtil.showDistance(data.distance?.toString()!!)
        mPresenter?.getPercentDistance()
        when (data.type) {
            Constant.CONVENIENT_TRIP -> {
                onConvenientTrip(false)
            }
            Constant.PRIVATE_TRIP -> {
                onPrivateTrip(false)
            }
            Constant.BOTH_CONVENIENT_AND_PRIVATE -> {
                if (data.userBooks?.size == 0) {
                    initWrapperItem()
                    onBothTrip()
                } else {
                    onConvenientTrip(true)
                }
            }
        }
        edt_number_seat.setText(data.emptySeat?.toString())
        mPresenter?.setSeatSpinner(data.emptySeat)
    }

    private fun initWrapperItem() {
        listItem.add(WrapperItem(tv_convinent, Constant.CONVENIENT_TRIP, true))
        listItem.add(WrapperItem(tv_private, Constant.PRIVATE_TRIP, true))
    }

    private fun onConvenientTrip(isBoth: Boolean) {
        setBackgroundTypeTrip(Constant.CONVENIENT_TRIP)
        row_one_seat.visible()
        rl_number_seat.visible()
        if (!isBoth) {
            tv_convinent.visible()
            tv_private.gone()
        } else {
            sp_number_seat.clearFocus()
            sp_number_seat.post({ sp_number_seat.setSelection(0) })
            edt_number_seat.setText((1).toString())
            mPresenter?.setNumberSeat(1)
        }
    }

    private fun onPrivateTrip(isBoth: Boolean) {
        setBackgroundTypeTrip(Constant.PRIVATE_TRIP)
        if (!isBoth) {
            tv_convinent.gone()
            tv_private.visible()
        }
        row_one_seat.gone()
        rl_number_seat.gone()
        mPresenter?.getPrice()
    }

    private fun onBothTrip() {
        setBackgroundTypeTrip(Constant.CONVENIENT_TRIP)
        tv_convinent.visible()
        tv_private.visible()
        rl_number_seat.visible()
        row_one_seat.visible()
        mPresenter?.getPrice()
    }

    override fun showPriceConvenient(oneSeatPrice: Int, totalPrice: Int) {
        row_one_seat.setContent(NumberUtil.formatNumber(oneSeatPrice.toString()))
        row_total_money.setContent(NumberUtil.formatNumber(totalPrice.toString()))
    }

    override fun showPricePrivate(totalPrice: Int) {
        row_total_money.setContent(NumberUtil.formatNumber(totalPrice.toString()))
    }

    private fun setBackgroundTypeTrip(type: Int?) {
        mPresenter?.setTypeTrip(type!!)
        for ((index, item) in listItem.withIndex()) {
            if (item.item == type) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    item.textView.background = resources.getDrawable(R.drawable.bg_btn_search, null)
                } else {
                    item.textView.background = resources.getDrawable(R.drawable.bg_btn_search)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    item.textView.background = resources.getDrawable(R.drawable.bg_btn_no_active_confirm_booking, null)
                } else {
                    item.textView.background = resources.getDrawable(R.drawable.bg_btn_no_active_confirm_booking)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == MapSearchActivity.RESULT_CODE) {
            if (requestCode == REQUEST_CODE_MAP_SEARCH) {
                mLocationStartingPointId = data?.extras?.getString(MapSearchActivity.STARTING_LOCATION_POINT_ID)
                mLocationEndingPointId = data?.extras?.getString(MapSearchActivity.ENDING_LOCATION_POINT_ID)
                mLocationStartingPoint = data?.extras?.getString(MapSearchActivity.STARTING_LOCATION_POINT)
                mLocationEndingPoint = data?.extras?.getString(MapSearchActivity.ENDING_LOCATION_POINT)
                if (!mLocationEndingPoint.isNullOrEmpty() && !mLocationEndingPoint.isNullOrEmpty()) {
                    tv_starting_point.text = (mLocationStartingPoint)
                    tv_ending_point.text = (mLocationEndingPoint)
                    mPresenter?.fetchDataPlaceById(mLocationStartingPointId, mLocationEndingPointId)
                }
            }
        }
    }

    override fun routeSuccess(route: Route) {
        tv_distance.text = route.distance
    }

    override fun showNumberSeat(numberSeat: Int) {
        edt_number_seat.setText(numberSeat.toString())
    }

    override fun routeFail() {
        ToastUtil.show("Có lỗi xảy ra, vui lòng thử lại sau!")
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

    private fun onDateRequestFocus(error: String) {
        tv_starting_date.error = error
        tv_starting_date.requestFocus()
    }

    private fun onHourRequestFocus(error: String) {
        tv_time.error = error
        tv_time.requestFocus()
    }

    private fun onCheckedCondition(): Boolean {
        return cb_condition.isChecked
    }

    override fun showPercentDistance(percentage: Double) {
        tv_percent.text = NumberUtil.showPercentage(percentage)
    }
}