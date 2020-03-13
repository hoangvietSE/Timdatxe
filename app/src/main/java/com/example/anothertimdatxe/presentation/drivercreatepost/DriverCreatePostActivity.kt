package com.example.anothertimdatxe.presentation.drivercreatepost

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TimePicker
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerSeatAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.common.AmountTextWatcher
import com.example.anothertimdatxe.entity.WrapperItem
import com.example.anothertimdatxe.entity.response.DriverCarResponse
import com.example.anothertimdatxe.entity.response.DriverPostDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.request.DriverCreatePostRequest
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.widget.TimePickerDialogWidget
import com.google.android.gms.maps.model.LatLng
import com.soict.hoangviet.baseproject.extension.toast
import kotlinx.android.synthetic.main.activity_driver_create_post.*
import kotlinx.android.synthetic.main.layout_convinent_trip.*
import kotlinx.android.synthetic.main.layout_private_trip.*

class DriverCreatePostActivity : BaseActivity<DriverCreatePostPresenterImpl>(), DriverCreatePostView {
    companion object {
        const val EXTRA_IS_CREATE_POST = "extra_is_create_post"
        const val EXTRA_IS_SHOW_DATA = "extra_is_show_data"
        const val EXTRA_POST_ID = "extra_driver_id"
        const val EXTRA_STARTING_POINT = "extra_starting_point"
        const val EXTRA_ENDING_POINT = "extra_ending_point"
        const val EXTRA_DISTANCE = "extra_distance"
        const val EXTRA_DURATION = "extra_duration"
        const val EXTRA_LIST_WAYPOINT = "extra_list_waypoint"
        const val ITEM_TYPE_CONVINENT = 0
        const val ITEM_TYPE_PRIVATE = 1
        const val ITEM_TYPE_BOTH = 2
    }

    private var driverId: Int = -1
    private var startingPoint: String? = null
    private var endingPoint: String? = null
    private var distance: String? = null
    private var duration: Int? = null
    private var listWayPoint: ArrayList<LatLng> = arrayListOf()
    private var mSpinnerCarBrandAdapter: SpinnerSeatAdapter? = null
    private var mSpinnerSeatAdapter: SpinnerSeatAdapter? = null
    private var mListCarBrand: MutableList<String> = mutableListOf()
    private var mListDriverCarInfo: List<DriverCarResponse>? = null
    private var mListSeat: MutableList<String> = mutableListOf()
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mTimePickerDialogWidget: TimePickerDialogWidget? = null
    private var listWrapperItem: MutableList<WrapperItem> = mutableListOf()
    private var numberSeat: Int? = null
    private var itemType: Int? = null
    private var carId: Int? = null
    private var data: DriverPostDetailResponse? = null

    private var convenient30TextWatcher: AmountTextWatcher? = null
    private var convenient50TextWatcher: AmountTextWatcher? = null
    private var convenient70TextWatcher: AmountTextWatcher? = null
    private var convenient100TextWatcher: AmountTextWatcher? = null
    private var private50TextWatcher: AmountTextWatcher? = null
    private var private100TextWatcher: AmountTextWatcher? = null

    override val layoutRes: Int
        get() = R.layout.activity_driver_create_post

    override fun getPresenter(): DriverCreatePostPresenterImpl {
        return DriverCreatePostPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        setBanner()
        initWrapperItem()
        getDataIntent()
    }

    override fun setListener() {
        edt_starting_point.setOnClickListener {
            mDatePickerDialogWidget = DatePickerDialogWidget(this, object : DatePickerDialogWidget.OnSetDateSuccessListener {
                override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
                    edt_starting_point.setText("" +
                            "${DateUtil.formatValue(dayOfMonth.toString())}/" +
                            "${DateUtil.formatValue(month.toString())}/" +
                            "${DateUtil.formatValue(year.toString())}")
                }

            })
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        edt_time.setOnClickListener {
            mTimePickerDialogWidget = TimePickerDialogWidget(this, object : TimePickerDialogWidget.OnTimeSetListener {
                override fun onSetTimeSuccess(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    edt_time.setText(
                            "$hourOfDay:$minute"
                    )
                }
            })
            mTimePickerDialogWidget?.showTimePickerDialog()
        }
        tv_convinent_trip.setOnClickListener {
            onSelectedItem(ITEM_TYPE_CONVINENT)

        }
        tv_private_trip.setOnClickListener {
            onSelectedItem(ITEM_TYPE_PRIVATE)

        }
        tv_both_trip.setOnClickListener {
            onSelectedItem(ITEM_TYPE_BOTH)
        }
        edt_30_percent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                edt_50_percent.requestFocus()
                true
            }
            false
        }
        edt_50_percent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                edt_70_percent.requestFocus()
                true
            }
            false
        }
        edt_70_percent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                edt_100_percent.requestFocus()
                true
            }
            false
        }
        edt_100_percent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                edt_private_50_percent.requestFocus()
                true
            }
            false
        }
        edt_private_50_percent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                edt_private_100_percent.requestFocus()
                true
            }
            false
        }
        btn_create_post.setOnClickListener {
            val request = DriverCreatePostRequest()
            request.title = edt_title.text.toString()
            request.empty_seat = numberSeat
            when (itemType) {
                ITEM_TYPE_CONVINENT -> {
                    request.type = ITEM_TYPE_CONVINENT
                    request.regular_price = edt_100_percent.text.toString()
                    request.price_level_1 = edt_30_percent.text.toString()
                    request.price_level_2 = edt_50_percent.text.toString()
                    request.price_level_3 = edt_70_percent.text.toString()
                }
                ITEM_TYPE_PRIVATE -> {
                    request.type = ITEM_TYPE_PRIVATE
                    request.private_price_1 = edt_private_50_percent.text.toString()
                    request.private_price_2 = edt_private_100_percent.text.toString()
                }
                ITEM_TYPE_BOTH -> {
                    request.type = ITEM_TYPE_BOTH
                    request.regular_price = edt_100_percent.text.toString()
                    request.price_level_1 = edt_30_percent.text.toString()
                    request.price_level_2 = edt_50_percent.text.toString()
                    request.price_level_3 = edt_70_percent.text.toString()
                    request.private_price_1 = edt_private_50_percent.text.toString()
                    request.private_price_2 = edt_private_100_percent.text.toString()
                }
            }
            request.date = edt_starting_point.text.toString()
            request.time = edt_time.text.toString()
            request.car_id = carId.toString()
            request.lat_from = listWayPoint[0].latitude.toString()
            request.lng_from = listWayPoint[0].longitude.toString()
            request.lat_to = listWayPoint[listWayPoint.size - 1].latitude.toString()
            request.lng_to = listWayPoint[listWayPoint.size - 1].longitude.toString()
            request.description = edt_des.text.toString()
            request.schedule = ""
            request.start_point = startingPoint
            request.end_point = endingPoint
            request.waypoints = getWayPoints()
            request.distance = getDistance(distance!!)
            request.duration_time = if (edt_estimate.text.toString().isEmpty()) null else edt_estimate.text.toString().toDouble()
            mPresenter?.driverCreatePost(request)

        }
    }

    private fun initWrapperItem() {
        listWrapperItem.add(WrapperItem(tv_convinent_trip, ITEM_TYPE_CONVINENT, true))
        listWrapperItem.add(WrapperItem(tv_private_trip, ITEM_TYPE_PRIVATE, false))
        listWrapperItem.add(WrapperItem(tv_both_trip, ITEM_TYPE_BOTH, false))
        onSelectedItem(ITEM_TYPE_CONVINENT)
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.driver_create_post_title).toUpperCase()
        }
    }

    private fun setBanner() {
        GlideApp.with(this)
                .load(R.drawable.bg_driver_post)
                .error(R.drawable.bg_white)
                .placeholder(R.drawable.bg_white)
                .into(imv_banner)
    }

    private fun getDataIntent() {
        startingPoint = intent.getStringExtra(EXTRA_STARTING_POINT)
        endingPoint = intent.getStringExtra(EXTRA_ENDING_POINT)
        distance = intent.getStringExtra(EXTRA_DISTANCE)
        duration = intent.getIntExtra(EXTRA_DURATION, -1)
        driverId = intent.getIntExtra(EXTRA_POST_ID, -1)
        if (intent.getBooleanExtra(EXTRA_IS_CREATE_POST, false)) {
            listWayPoint = intent.extras.getParcelableArrayList(EXTRA_LIST_WAYPOINT)
            setDataIntent()
            getDriverCarInfo()
        }
        if (intent.getBooleanExtra(EXTRA_IS_SHOW_DATA, false)) {
            if (driverId != -1) {
                mPresenter?.fetData(driverId)
            }
        }
    }

    private fun setDataIntent() {
        tv_starting_point.text = startingPoint
        tv_ending_point.text = endingPoint
        tv_distance.text = distance.toString()
    }

    private fun getDriverCarInfo() {
        mPresenter?.fetchDriverCarInfo()
    }

    override fun initSpinner(list: List<DriverCarResponse>) {
        mListDriverCarInfo = list
        mListCarBrand.add("Chọn loại xe")
        list.forEach {
            mListCarBrand.add(it.fullName!!)
        }
        mSpinnerCarBrandAdapter = SpinnerSeatAdapter(this, mListCarBrand)
        mSpinnerSeatAdapter = SpinnerSeatAdapter(this, mutableListOf())
        sp_brand_car.adapter = mSpinnerCarBrandAdapter
        sp_number_seat.adapter = mSpinnerSeatAdapter
        sp_number_seat.isEnabled = false
        sp_brand_car.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSpinnerSeatAdapter?.clear()
                mListSeat.clear()
                if (position == 0) {
                    sp_number_seat.isEnabled = false
                    carId = -1
                } else {
                    setSeatNumber(mListDriverCarInfo?.get(position - 1)?.seatNumber!!)
                    sp_number_seat.isEnabled = true
                    carId = list.get(position - 1).id
                }
            }
        }
        sp_number_seat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                numberSeat = mListSeat.get(position).toInt()
            }
        }
    }

    override fun showDataCreatedPost(data: DriverPostDetailResponse) {
        this.data = data
        tv_starting_point.text = data.startPoint
        tv_ending_point.text = data.endPoint
        edt_title.setText(data.title)
        tv_distance.text = data.distance.toString() + " km"
        edt_starting_point.setText(DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_23))
        edt_time.setText(DateUtil.formatDate(data.startTime, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_15))
        edt_estimate.setText(data.durationTime.toString())
        mListCarBrand.forEachIndexed { index, carName ->
            if (carName == data.car?.name) {
                sp_brand_car.setSelection(index)
            }
        }
        setSeatNumber(data.car?.seatNumber!!)
        sp_number_seat.setSelection(data.createdSeat!! - 1)
        if (data.high_way == 1) {
            cb_highway.isChecked = true
        } else if (data.high_way == 0) {
            cb_highway.isChecked = false
        }
        handleTypeTrip(data.type)
        edt_des.setText(data.description ?: "")
        if (data.flagEdit == 1) {
            enableWidget(true)
        } else if (data.flagEdit == 0) {
            enableWidget(false)
        }
        if (data.flagDelete == 1) {
//            btn_status.visible()
//            btn_status.text = "XÓA BÀI ĐĂNG"
            btn_create_post.background.level = 1
        } else if (data.flagDelete == 0) {
//            btn_status.gone()
        }
        if (data.flagShowListBook == 1) {
            btn_create_post.visible()
            btn_create_post.text = "DANH SÁCH KHÁCH ĐẶT"
            btn_create_post.background.level = 0
        } else if (data.flagShowListBook == 0) {
            btn_create_post.gone()
        }
        if (data.status == Constant.DRIVER_POST_CANCEL) {
            when (data.reason) {
                Constant.DRIVER_CANCEL_POST -> {
//                    btn_status.visible()
//                    btn_status.text = "TÀI XE HỦY BÀI ĐĂNG"
                    btn_create_post.background.level = 1
                }
                Constant.DRIVER_CANCEL_BOOKING -> {
//                    btn_status.visible()
//                    btn_status.text = "TÀI XẾ HỦY BOOKING"
                    btn_create_post.background.level = 1
                }
                Constant.POST_EXPIRE -> {
//                    btn_status.visible()
//                    btn_status.text = "CHUYẾN ĐI HẾT HẠN"
                    btn_create_post.background.level = 1
                }
            }
        }

    }

    private fun enableWidget(check: Boolean) {
        edt_title.isEnabled = check
        edt_starting_point.isEnabled = check
        edt_time.isEnabled = check
        edt_estimate.isEnabled = check
        sp_brand_car.isClickable = check
        sp_number_seat.isClickable = check
        cb_highway.isEnabled = check
        if (data?.type == ITEM_TYPE_CONVINENT || data?.type == ITEM_TYPE_BOTH) {
            edt_30_percent.isEnabled = check
            edt_50_percent.isEnabled = check
            edt_70_percent.isEnabled = check
            edt_100_percent.isEnabled = check
        }
        if (data?.type == ITEM_TYPE_PRIVATE || data?.type == ITEM_TYPE_BOTH) {
            edt_private_50_percent.isEnabled = check
            edt_private_100_percent.isEnabled = check
        }
    }

    private fun handleTypeTrip(type: Int?) {
        tv_both_trip.gone()
        tv_convinent_trip.gone()
        tv_private_trip.gone()
        showFormMoney(type!!)
        when (type) {
            ITEM_TYPE_CONVINENT -> {
                tv_convinent_trip.visible()
                onSelectedItem(ITEM_TYPE_CONVINENT)
                setPriceConvinent()
            }
            ITEM_TYPE_PRIVATE -> {
                tv_private_trip.visible()
                onSelectedItem(ITEM_TYPE_PRIVATE)
                setPricePrivate()
            }
            ITEM_TYPE_BOTH -> {
                tv_convinent_trip.visible()
                tv_private_trip.visible()
                onSelectedItem(ITEM_TYPE_CONVINENT)
                setPriceConvinent()
                setPricePrivate()
            }
        }
    }

    private fun setPricePrivate() {
        edt_private_50_percent.setText(NumberUtil.formatNumber(data?.privatePrice1!!))
        edt_private_100_percent.setText(NumberUtil.formatNumber(data?.privatePrice2!!))
    }

    private fun setPriceConvinent() {
        edt_30_percent.setText(NumberUtil.formatNumber(data?.priceLevel1.toString()))
        edt_50_percent.setText(NumberUtil.formatNumber(data?.priceLevel2.toString()))
        edt_70_percent.setText(NumberUtil.formatNumber(data?.priceLevel3.toString()))
        edt_100_percent.setText(NumberUtil.formatNumber(data?.regularPrice.toString()))
    }

    fun setSeatNumber(seat: Int) {
        for (i in 1..seat) {
            mListSeat.add(i.toString())
        }
        mSpinnerSeatAdapter?.addAll(mListSeat)
    }

    fun onSelectedItem(item: Int) {
        itemType = item
        for (i in 0..listWrapperItem.size - 1) {
            if (i == item) {
                listWrapperItem.get(i).isSelected = true
                setBackgroundSelected(i)
            } else {
                listWrapperItem.get(i).isSelected = false
                setBackgroundUnselected(i)
            }
        }
        showFormMoney(item)
    }

    fun showFormMoney(item: Int) {
        when (item) {
            ITEM_TYPE_CONVINENT -> {
                form_convinent.visible()
                form_private.gone()
                addTextWatcherConvinentTrip()
                removeTextWatcherPrivateTrip()
            }
            ITEM_TYPE_PRIVATE -> {
                form_convinent.gone()
                form_private.visible()
                removeTextWatcherConvinentTrip()
                addTextWatcherPrivateTrip()
            }
            ITEM_TYPE_BOTH -> {
                form_convinent.visible()
                form_private.visible()
                addTextWatcherConvinentTrip()
                addTextWatcherPrivateTrip()
            }
        }
    }

    private fun addTextWatcherConvinentTrip() {
        convenient30TextWatcher?.let {
            edt_30_percent.addTextChangedListener(it)
        } ?: run {
            convenient30TextWatcher = AmountTextWatcher(edt_30_percent)
            edt_30_percent.addTextChangedListener(convenient30TextWatcher)
        }
        convenient50TextWatcher?.let {
            edt_50_percent.addTextChangedListener(it)
        } ?: run {
            convenient50TextWatcher = AmountTextWatcher(edt_50_percent)
            edt_50_percent.addTextChangedListener(convenient50TextWatcher)
        }
        convenient70TextWatcher?.let {
            edt_70_percent.addTextChangedListener(it)
        } ?: run {
            convenient70TextWatcher = AmountTextWatcher(edt_70_percent)
            edt_70_percent.addTextChangedListener(convenient70TextWatcher)
        }
        convenient100TextWatcher?.let {
            edt_100_percent.addTextChangedListener(it)
        } ?: run {
            convenient100TextWatcher = AmountTextWatcher(edt_100_percent)
            edt_100_percent.addTextChangedListener(convenient100TextWatcher)
        }
    }

    private fun addTextWatcherPrivateTrip() {
        private50TextWatcher?.let {
            edt_private_50_percent.addTextChangedListener(it)
        } ?: run {
            private50TextWatcher = AmountTextWatcher(edt_private_50_percent)
            edt_private_50_percent.addTextChangedListener(private50TextWatcher)
        }
        private100TextWatcher?.let {
            edt_private_100_percent.addTextChangedListener(it)
        } ?: run {
            private100TextWatcher = AmountTextWatcher(edt_private_100_percent)
            edt_private_100_percent.addTextChangedListener(private100TextWatcher)
        }
    }

    private fun removeTextWatcherConvinentTrip() {
        convenient30TextWatcher?.let {
            edt_30_percent.removeTextChangedListener(it)
        }
        convenient50TextWatcher?.let {
            edt_50_percent.removeTextChangedListener(it)
        }
        convenient70TextWatcher?.let {
            edt_70_percent.removeTextChangedListener(it)
        }
        convenient100TextWatcher?.let {
            edt_100_percent.removeTextChangedListener(it)
        }
    }

    private fun removeTextWatcherPrivateTrip() {
        private50TextWatcher?.let {
            edt_private_50_percent.removeTextChangedListener(it)
        }
        private100TextWatcher?.let {
            edt_private_100_percent.removeTextChangedListener(it)
        }
    }

    fun setBackgroundSelected(item: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listWrapperItem.get(item).textView.background = resources.getDrawable(R.drawable.bg_btn_book_car, null)
        } else {
            listWrapperItem.get(item).textView.background = resources.getDrawable(R.drawable.bg_btn_book_car)
        }
    }

    fun setBackgroundUnselected(item: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listWrapperItem.get(item).textView.background = resources.getDrawable(R.drawable.bg_type_trip_create_post, null)
        } else {
            listWrapperItem.get(item).textView.background = resources.getDrawable(R.drawable.bg_type_trip_create_post)
        }
    }

    private fun getWayPoints(): String {
        var wayPoints = ""
        var count = 0
        for (item in listWayPoint) {
            if (count == listWayPoint.size - 1) {
                wayPoints = wayPoints + item.latitude + ","
                wayPoints += item.longitude
            } else {
                wayPoints = wayPoints + item.latitude + ","
                wayPoints = wayPoints + item.longitude + ","
                count++
            }
        }
        return wayPoints
    }

    fun getDistance(distance: String): Double? {
        return try {
            var newDistance: String? = null
            if (distance.contains(" km")) {
                newDistance = distance.replace(" km", "")
            }
            if (distance.contains(",")) {
                newDistance = distance.replace(",", "")
            }
            return newDistance?.toDouble()
        } catch (e: NumberFormatException) {
            return 0.0
        }
    }

    fun getDuration(time: String): Double {
        var newTime = ""
        for (i in 0..time.length - 1) {
            if (time.elementAt(i) == ' ') break
            newTime = newTime + time.elementAt(i)
        }
        return newTime.toDouble()
    }

    override fun onErrorNoTitle() {
        toast(resources.getString(R.string.driver_create_post_no_title))
    }

    override fun onErrorTitleHaveNumber() {
        toast(resources.getString(R.string.driver_create_post_have_number))
    }

    override fun onErrorNoDate() {
        toast(resources.getString(R.string.driver_create_post_no_date))
    }

    override fun onErrorNoTime() {
        toast(resources.getString(R.string.driver_create_post_no_time))
    }

    override fun onErrorDateInPast() {
        toast(resources.getString(R.string.driver_create_post_in_past))
    }

    override fun onErrorNoEstimate() {
        toast(resources.getString(R.string.driver_create_post_no_estimate))
    }

    override fun onErrorInvalidEstimate() {
        toast(resources.getString(R.string.driver_create_post_invalid_estimate))
    }

    override fun onErrorNoCarBrand() {
        toast(resources.getString(R.string.driver_create_post_no_car_brand))
    }

    override fun onErrorNoMoney() {
        toast(resources.getString(R.string.driver_create_post_no_money))
    }

    override fun onErrorInvalidMoney() {
        toast(resources.getString(R.string.driver_create_post_invalid_money))
    }

    override fun onErrorMinMoney() {
        toast(resources.getString(R.string.driver_create_post_min_money))
    }

    override fun onSuccessCreatePost() {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

}