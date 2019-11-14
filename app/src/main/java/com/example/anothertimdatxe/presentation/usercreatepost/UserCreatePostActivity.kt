package com.example.anothertimdatxe.presentation.usercreatepost

import android.app.Activity
import android.util.Log
import android.widget.TimePicker
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.request.UserCreatePostRequest
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.MapUtil
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.widget.TimePickerDialogWidget
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_user_create_post.*
import java.util.*

class UserCreatePostActivity : BaseActivity<UserCreatePostPresenter>(), UserCreatePostView,
        DatePickerDialogWidget.OnSetDateSuccessListener,
        TimePickerDialogWidget.OnTimeSetListener {
    companion object {
        val TAG: String = UserCreatePostActivity::class.java.simpleName
        const val EXTRA_STARTING_POINT = "extra_starting_point"
        const val EXTRA_ENDING_POINT = "extra_ending_point"
        const val EXTRA_DISTANCE = "extra_distance"
        const val EXTRA_DURATION = "extra_duration"
        const val EXTRA_LIST_WAYPOINT = "extra_list_waypoint"
        const val EXTRA_IS_CREATE_POST = "extra_is_create_post"
    }

    private var startingPoint: String? = null
    private var endingPoint: String? = null
    private var distance: String? = null
    private var duration: Int? = null
    private var durationTimeInDouble: Double? = null
    private var listWayPoint: ArrayList<LatLng> = arrayListOf()
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mTimePickerDialogWidget: TimePickerDialogWidget? = null

    override val layoutRes: Int
        get() = R.layout.activity_user_create_post

    override fun getPresenter(): UserCreatePostPresenter {
        return UserCreatePostPresenterImpl(this)
    }

    override fun initView() {
        setLayoutToolbar()
        getDataIntent()
        initDatePicker()
    }

    private fun initDatePicker() {
        mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
        mTimePickerDialogWidget = TimePickerDialogWidget(this, this)
    }

    override fun setListener() {
        btn_create_post.setOnClickListener {
            callApiCreatePost()
        }
        tv_date.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        tv_hour.setOnClickListener {
            mTimePickerDialogWidget?.showTimePickerDialog()
        }
    }

    private fun callApiCreatePost() {
        try {
            val data = UserCreatePostRequest()
            data.distance = MapUtil.getDistance(distance!!).toDouble()
            data.title = edt_title.text.toString()
            data.slug = ""
            if (tv_date.text.toString().isEmpty()) {
                onDateEmpty()
                return
            }
            if (tv_hour.text.toString().isEmpty()) {
                onTimeEmpty()
                return
            }
            if (edt_number_seat.text.toString().isEmpty()) {
                onSeatEmpty()
                return
            }
            data.start_time = DateUtil.formatDate("${tv_hour.text} ${tv_date.text}", DateUtil.DATE_FORMAT_11, DateUtil.DATE_FORMAT_13)
            data.duration_time = duration
            data.number_seat = edt_number_seat.text.toString().toInt()
            data.description = edt_des.text.toString()
            data.lat_from = listWayPoint[0].latitude.toString()
            data.lng_from = listWayPoint[0].longitude.toString()
            data.lat_to = listWayPoint[listWayPoint.size - 1].latitude.toString()
            data.lng_to = listWayPoint[listWayPoint.size - 1].longitude.toString()
            data.start_point = startingPoint
            data.end_point = endingPoint
            getWayPoints(data)
            mPresenter?.createPost(data)
        } catch (e: Exception) {
            Log.d(TAG, "CREATE_POST_ERROR")
        }
    }

    private fun getWayPoints(data: UserCreatePostRequest) {
        mPresenter?.getWayPoints(data, listWayPoint)
    }

    private fun getDataIntent() {
        startingPoint = intent.getStringExtra(EXTRA_STARTING_POINT)
        endingPoint = intent.getStringExtra(EXTRA_ENDING_POINT)
        distance = intent.getStringExtra(EXTRA_DISTANCE)
        duration = intent.getIntExtra(EXTRA_DURATION, -1)
        durationTimeInDouble = duration?.let {
            it.toDouble() / 60 / 60
        }
        if (intent.getBooleanExtra(EXTRA_IS_CREATE_POST, false)) {
            listWayPoint = intent.extras.getParcelableArrayList(EXTRA_LIST_WAYPOINT)
            setDataByGetIntent()
        }
    }

    private fun setDataByGetIntent() {
        tv_starting_point.text = startingPoint
        tv_ending_point.text = endingPoint
        tv_distance.text = distance
        tv_duration.text = String.format("%.1f", durationTimeInDouble)
    }

    private fun setLayoutToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.driver_create_post_title).toUpperCase()
        }
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val startingCalendar = Calendar.getInstance()
        startingCalendar.set(year, month - 1, dayOfMonth)
        if (calendar.after(startingCalendar) || calendar.equals(startingCalendar)) {
            onDateError()
            return
        }
        tv_date.text = "${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}/${DateUtil.formatValue(year.toString())}"
    }

    override fun onSetTimeSuccess(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tv_hour.text = "${DateUtil.formatValue(hourOfDay.toString())}:${DateUtil.formatValue(minute.toString())}"
    }

    override fun showWayPoints(data: UserCreatePostRequest, wayPoints: String) {
        data.waypoints = wayPoints
    }

    override fun onTitleEmpty() {
        ToastUtil.show(resources.getString(R.string.user_create_post_title_empty))
    }

    override fun onTitleError() {
        ToastUtil.show(resources.getString(R.string.user_create_post_title_error))
    }

    override fun onDateEmpty() {
        ToastUtil.show(resources.getString(R.string.user_create_post_date_empty))
    }

    override fun onDateError() {
        ToastUtil.show(resources.getString(R.string.user_create_post_date_error))
    }

    override fun onTimeEmpty() {
        ToastUtil.show(resources.getString(R.string.user_create_post_time_empty))
    }

    override fun onSeatEmpty() {
        ToastUtil.show(resources.getString(R.string.user_create_post_seat_empty))
    }

    override fun onSeatError() {
        ToastUtil.show(resources.getString(R.string.user_create_post_seat_error))
    }

    override fun onCreatePostError() {
        ToastUtil.show(resources.getString(R.string.user_create_post_error))
    }

    override fun onCreatePostSuccess() {
        ToastUtil.show(resources.getString(R.string.user_create_post_success))
        setResult(Activity.RESULT_OK)
        finish()
    }
}