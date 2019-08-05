package com.example.anothertimdatxe.sprinthome.listrequest.driver.detail

import android.content.DialogInterface
import android.os.Build
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.UserPostDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.*
import kotlinx.android.synthetic.main.activity_detail_request.*

class DriverRequestDetailActivity : BaseActivity<DriverRequestDetailPresenter>(), DriverRequestDetailView {
    private var id: Int? = null

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
        tv_distance.text = data.distance!!.toString()
        if (data.driver_book != null) {
            formCar.visible()
            tv_car_name.text = data.driver_book?.full_name
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
                .centerCrop()
                .into(imv_avatar)
        if (data.driver_book?.status == Constant.DRIVER_BOOK_ACCEPTED) {
            formMoney.visible()
            formPhone.visible()
            tv_money.text = NumberUtil.formatNumber(data.driverBookTotalPrice!!)
            tv_phone.text = data.user!!.phone
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
        }
        if (data.driver_can_request != null && data.driver_can_request) {
            tv_request.setOnClickListener {
                if (!avoidDoubleClick()) {

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
}