package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.AdapterView
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerCarAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverCar
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.*
import com.example.anothertimdatxe.widget.NumberTextWatcher
import kotlinx.android.synthetic.main.activity_detail_request.*
import kotlinx.android.synthetic.main.dialog_driver_book_request.*

class DriverRequestDetailActivity : BaseActivity<DriverRequestDetailPresenter>(), DriverRequestDetailView {
    private var id: Int? = null
    private var response: UserPostDetailResponse? = null
    private var mSpinnerAdapter: SpinnerCarAdapter? = null

    companion object {
        const val USER_POST_ID = "id"
    }

    override val layoutRes: Int
        get() = R.layout.activity_detail_request

    override fun getPresenter(): DriverRequestDetailPresenter {
        return DriverRequestDetailPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        getDataIntent()
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            layout_action.visible()
        } else {
            layout_action.gone()
        }
        fetchData()
    }

    private fun fetchData() {
        mPresenter!!.getDataUserPost(id!!)
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.driver_request_detail_toolbar_title).toUpperCase()
        }
        leftbutton?.let {
            it.setImageResource(R.drawable.ic_arrow_back_black_24dp)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimary, null))
        } else {
            toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }
    }


    private fun getDataIntent() {
        id = intent.getIntExtra(USER_POST_ID, -1)
    }

    override fun showDetailUserPost(data: UserPostDetailResponse) {
        response = data
        tv_starting_point.text = data.start_point
        tv_ending_point.text = data.end_point
        data.code?.let {
            formCode.visible()
            tv_code.text = it
        } ?: run {
            formCode.gone()
        }
        tv_user_request.text = data.description
        tv_time_start.text = DateUtil.formatDate(data.start_time!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_24)
        tv_seat.text = data.number_seat!!.toString()
        tv_distance.text = NumberUtil.showDistance(data.distance!!.toString())
        if (data.driver_book != null) {
            formCar.visible()
            tv_car_name.text = data.driver_book?.full_name
            tv_car_name.paintFlags = (tv_car_name.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
            tv_car_name.setOnClickListener {
                if (!avoidDoubleClick()) {

                }
            }
            tv_status.text = data.driver_book?.str_status
            when (data.driver_book?.status) {
                Constant.DRIVER_BOOK_PENDING -> {
                    imv_status.setImageResource(R.drawable.ic_status_pending)
                }
                Constant.DRIVER_BOOK_ACCEPTED -> {
                    imv_status.setImageResource(R.drawable.ic_status_reject)
                }
                Constant.DRIVER_BOOK_FINISH -> {
                    imv_status.setImageResource(R.drawable.ic_status_finish)
                }
                Constant.DRIVER_BOOK_CANCEL, Constant.DRIVER_BOOK_REJECTED -> {
                    imv_status.setImageResource(R.drawable.ic_status_cancel)
                }
            }
        } else {
            formCar.gone()
            tv_status.text = data.str_status
            when (data.status) {
                Constant.USER_POST_PUBLISHED -> {
                    imv_status.setImageResource(R.drawable.ic_status_pending)
                }
                Constant.USER_POST_DONE -> {
                    imv_status.setImageResource(R.drawable.ic_status_reject)
                }
                Constant.USER_POST_FINISH -> {
                    imv_status.setImageResource(R.drawable.ic_status_finish)
                }
                Constant.USER_POST_CANCEL -> {
                    imv_status.setImageResource(R.drawable.ic_status_cancel)
                }
            }
        }
        tv_name.text = data.user!!.fullName
        tv_address.text = data.user!!.address
        GlideApp.with(this)
                .load(BuildConfig.BASE_URL + "/" + data.user!!.avatar)
                .placeholder(if (data.user!!.gender.equals("0")) R.drawable.ic_avatar else R.drawable.ic_avatar_female)
                .error(if (data.user!!.gender.equals("0")) R.drawable.ic_avatar else R.drawable.ic_avatar_female)
                .circleCrop()
                .into(imv_avatar)
        if (data.driver_book?.status == Constant.DRIVER_BOOK_ACCEPTED) {
            formMoney.visible()
            formPhone.visible()
            tv_money.text = NumberUtil.formatNumber(data.driverBookTotalPrice!!)
            tv_phone.text = data.user!!.phone
            tv_phone.paintFlags = (tv_phone.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
            tv_phone.setOnClickListener {
                if (!avoidDoubleClick()) {
                    var callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.setData(Uri.parse("tel:${tv_phone.text}"))
                    startActivity(callIntent)
                }
            }
        } else if (data.driver_book?.status == Constant.DRIVER_BOOK_PENDING) {
            formPhone.gone()
            formMoney.visible()
            tv_money.text = NumberUtil.formatNumber(data.driver_book!!.price!!)
        } else {
            formMoney.gone()
            formPhone.gone()
        }
        if (data.canFinish == 1) {
            tv_finish_trip.visible()
            tv_finish_trip.setOnClickListener {
                if (!avoidDoubleClick()) {
                    mPresenter!!.finishTripDriverBook(response?.id!!)
                }
            }
        } else {
            tv_finish_trip.gone()
        }
        if (data.status == Constant.USER_POST_CANCEL) {
            formReason.visible()
            if (data.driver_book != null) {
                tv_reason.text = data.driver_book!!.str_reason
            } else {
                tv_reason.text = data.str_reason
            }
        } else {
            formReason.gone()
        }
        if (data.driver_can_request != null && data.driver_can_request) {
            tv_request.setOnClickListener {
                if (!avoidDoubleClick()) {
                    if (data.driverCarPending == 1) {
                        showConfirmRequestDialog(data.driverCars)
                    }
                }
            }
            tv_request.visible()
            tv_cancel_request.gone()
            tv_cancel_booking.gone()
        } else {
            tv_request.gone()
        }

        if (data.driver_book?.status == Constant.DRIVER_BOOK_PENDING) {
            tv_cancel_request.visible()
            tv_request.gone()
            tv_cancel_booking.gone()
            tv_cancel_request.setOnClickListener {
                if (!avoidDoubleClick()) {
                    DialogUtil.showAlertDialogNoTitle(
                            this,
                            resources.getString(R.string.driver_request_detail_dialog_message_cancel_request),
                            true,
                            resources.getString(R.string.driver_request_detail_dialog_positive),
                            resources.getString(R.string.driver_request_detail_dialog_negative),
                            object : DialogUtil.BaseAlertDialogListener {
                                override fun onPositiveClick(dialogInterface: DialogInterface) {
                                    dialogInterface.dismiss()
                                    mPresenter!!.cancelRequest(response?.driver_book!!.driver_book_option_id!!)
                                }

                                override fun onNegativeClick(dialogInterface: DialogInterface) {
                                    dialogInterface.dismiss()
                                }

                            }
                    )
                }
            }
        } else if (data.driver_book?.status == Constant.DRIVER_BOOK_ACCEPTED) {
            if (data.driverCancelBooking == 0) {
                //Không hủy khi dưới 1 ngày
                tv_cancel_booking.gone()
            } else {
                if (data.drivers!![0].id == CarBookingSharePreference.getUserId()) {
                    tv_cancel_booking.visible()
                    tv_cancel_booking.setOnClickListener {
                        if (!avoidDoubleClick()) {
                            DialogUtil.showAlertDialogNoTitle(
                                    this,
                                    resources.getString(R.string.driver_request_detail_dialog_message_cancel_booking),
                                    true,
                                    resources.getString(R.string.driver_request_detail_dialog_positive),
                                    resources.getString(R.string.driver_request_detail_dialog_negative),
                                    object : DialogUtil.BaseAlertDialogListener {
                                        override fun onPositiveClick(dialogInterface: DialogInterface) {
                                            dialogInterface.dismiss()
                                            mPresenter!!.cancelBooking(response?.driverBookId!!)
                                        }

                                        override fun onNegativeClick(dialogInterface: DialogInterface) {
                                            dialogInterface.dismiss()
                                        }

                                    }
                            )
                        }
                    }
                } else {
                    tv_request.gone()
                    tv_cancel_request.gone()
                    tv_cancel_booking.gone()
                }
            }
        } else {
            tv_cancel_booking.gone()
            tv_cancel_request.gone()
        }
        if (data.canBook == 0) {
            tv_request.gone()
        }
    }

    private fun showConfirmRequestDialog(driverCars: ArrayList<DriverCar>?) {
        var mCarId = -1
        if (driverCars?.size == 0) {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_no_regis_car))
            return
        }
        DialogUtil.showConfirmDialogDriverBookRequest(
                this,
                R.layout.dialog_driver_book_request,
                true,
                R.drawable.bg_white_10dp,
                object : DialogUtil.BaseDialogListener {
                    override fun onAddDataToDialog(context: Context, dialog: Dialog) {
                        mSpinnerAdapter = SpinnerCarAdapter(context, driverCars!!)
                        dialog.spinner.adapter = mSpinnerAdapter
                        dialog.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                mCarId = driverCars!![position].id!!
                            }

                        }
                        dialog.edt_money.addTextChangeListener(NumberTextWatcher(dialog.edt_money))
                    }

                    override fun onClickDialog(dialog: Dialog) {
                        if (mCarId == -1) {
                            ToastUtil.show(resources.getString(R.string.dialog_driver_book_request_no_car))
                        } else if (dialog.edt_money.text.toString().isNullOrEmpty() || dialog.edt_money.text.toString().isNullOrBlank()) {
                            dialog.edt_money.setError(resources.getString(R.string.dialog_driver_book_request_no_money))
                            dialog.edt_money.requestFocus()
                        } else if (NumberUtil.replaceNumber(dialog.edt_money.text.toString(), ",", "").toInt() < 1000) {
                            dialog.edt_money.setError(resources.getString(R.string.dialog_driver_book_request_min_value))
                            dialog.edt_money.requestFocus()
                        } else if (NumberUtil.isNumberString(dialog.edt_des.text.toString())) {
                            dialog.edt_des.setError(resources.getString(R.string.dialog_driver_book_request_no_number))
                            dialog.edt_des.requestFocus()
                        } else {
                            var mount: String = ""
                            dialog.edt_money.text.toString()?.let {
                                if (it.contains(",", false)) {
                                    mount = NumberUtil.replaceNumber(it, ",", "")
                                } else {
                                    mount = it
                                }
                            }
                            mPresenter?.bookUserPost(
                                    response?.user_id!!,
                                    mCarId,
                                    response?.id!!,
                                    mount,
                                    edt_des.text.toString()
                            )
                            dialog.dismiss()
                        }
                    }
                }
        )

    }

    override fun finishScreen() {
        finish()
    }

    override fun cancelRequestSuccess(check: Boolean) {
        if (check) {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_cancel_request_success))
        } else {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_cancel_request_fail))
        }
    }

    override fun cancelBookingSuccess(check: Boolean) {
        if (check) {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_cancel_booking_success))
        } else {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_cancel_booking_fail))
        }
    }

    override fun finishTripSucceess(check: Boolean) {
        if (check) {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_finish_trip_success))
        } else {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_finish_trip_fail))
        }
    }

    override fun finishBookSuccess(check: Boolean) {
        if (check) {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_apply_success))
        } else {
            ToastUtil.show(resources.getString(R.string.driver_request_detail_apply_fail))
        }
    }
}