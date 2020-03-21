package com.example.anothertimdatxe.sprinthome.listrequest.user.detail

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.UserRequestDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.presentation.map.mapshow.MapShowActivity
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.DialogUtil
import com.example.anothertimdatxe.util.NumberUtil
import com.soict.hoangviet.baseproject.extension.toast
import kotlinx.android.synthetic.main.activity_user_request_detail.*
import kotlinx.android.synthetic.main.dialog_confirm_cancel_booking_step_one.*
import kotlinx.android.synthetic.main.dialog_confirm_cancel_booking_step_two.*
import java.util.*

class UserRequestDetailActivity : BaseActivity<UserRequestDetailPresenter>(), UserRequestDetailView {
    companion object {
        const val EXTRA_POST_ID = "extra_user_id"
        const val ALLOW_CANCEL_BOOKING = 1
        const val RESULT_USER_CANCEL_REQUEST = 1001
    }

    private var postId: Int? = null
    private var mUserRequestDetailResponse: UserRequestDetailResponse? = null
    private var totalMoney: Int = 0
    private var mDialogStepTwo: Dialog? = null

    override val layoutRes: Int
        get() = R.layout.activity_user_request_detail

    override fun getPresenter(): UserRequestDetailPresenter {
        return UserRequestDetailPresenterImpl(this)
    }

    override fun initView() {
        getDataIntent()
        setToolbar()
        fetchUserRequestDetail()
    }

    override fun setListener() {
        btn_status.setOnClickListener {
            if (mUserRequestDetailResponse?.canCancelBooking == ALLOW_CANCEL_BOOKING) {
                showPolicyStepOne()
            }
        }
        btn_show_map.setOnClickListener {
            startActivity(Intent(this, MapShowActivity::class.java).apply {
                putExtra(MapShowActivity.LAT_FROM, mUserRequestDetailResponse?.driverPost?.latFrom?.toDouble())
                putExtra(MapShowActivity.LNG_FROM, mUserRequestDetailResponse?.driverPost?.lngFrom?.toDouble())
                putExtra(MapShowActivity.LAT_TO, mUserRequestDetailResponse?.driverPost?.latTo?.toDouble())
                putExtra(MapShowActivity.LNG_TO, mUserRequestDetailResponse?.driverPost?.lngTo?.toDouble())
                putExtra(MapShowActivity.ORIGIN_LOCATION, mUserRequestDetailResponse?.driverPost?.startPoint)
                putExtra(MapShowActivity.DESTINATION_LOCATION, mUserRequestDetailResponse?.driverPost?.endPoint)
            })
        }
    }

    private fun showPolicyStepOne() {
        val dialog = DialogUtil.showConfirmDiaglog(
                this,
                R.layout.dialog_confirm_cancel_booking_step_one,
                true,
                R.drawable.bg_white_10dp
        )
        dialog.imv_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btn_step_one.setOnClickListener {
            dialog.dismiss()
            showPolicyStepTwo()
        }
    }

    private fun showPolicyStepTwo() {
        val dialog = DialogUtil.showConfirmDialog(
                this,
                R.layout.dialog_confirm_cancel_booking_step_two,
                true,
                R.drawable.bg_white_10dp,
                object : DialogUtil.BaseDialogListener {
                    override fun onAddDataToDialog(context: Context, dialog: Dialog) {
                        mDialogStepTwo = dialog
                        val calendar = Calendar.getInstance(TimeZone.getDefault())
                        dialog.tv_starting_date.text = DateUtil.formatDate(mUserRequestDetailResponse?.userBook?.bookTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_24)
                        dialog.tv_current_date.text = DateUtil.formatDateToString(calendar.time, DateUtil.DATE_FORMAT_24)
                        val day = DateUtil.calculateDayBetweenTwoDate(dialog.tv_current_date.text.toString(), dialog.tv_starting_date.text.toString(), DateUtil.DATE_FORMAT_24)
                        val hours = DateUtil.calculateHoursBetweenTwoDate(dialog.tv_current_date.text.toString(), dialog.tv_starting_date.text.toString(), DateUtil.DATE_FORMAT_24)
                        dialog.tv_time_remain.text = "$day ngày $hours giờ"
                        dialog.tv_money_refund.text =
                                if (day > 5) {
                                    NumberUtil.formatNumber(totalMoney.toString())
                                } else if (day in 4..5) {
                                    NumberUtil.formatNumber((totalMoney * 0.7).toString())
                                } else if (day in 1..3) {
                                    NumberUtil.formatNumber((totalMoney * 0.5).toString())
                                } else {
                                    NumberUtil.formatNumber((totalMoney * 0).toString())
                                }
                    }

                    override fun onClickDialog(dialog: Dialog) {
                    }

                }
        )
        dialog.imv_cancel_step_two.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btn_step_two.setOnClickListener {
            callApiCancelRequest()
        }
    }

    private fun callApiCancelRequest() {
        mPresenter?.userCancelUserBook(mUserRequestDetailResponse?.userBook?.id!!)
    }

    private fun fetchUserRequestDetail() {
        mPresenter?.fetchUserRequestDetail(postId!!)
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.user_request_detail_toolbar_title).toUpperCase()
        }
    }

    private fun getDataIntent() {
        postId = intent.getIntExtra(EXTRA_POST_ID, -1)
    }

    override fun showUserRequestDetail(data: UserRequestDetailResponse) {
        mUserRequestDetailResponse = data
        tv_starting_point.text = data.driverPost?.startPoint
        tv_ending_point.text = data.driverPost?.endPoint
        row_code.setDetail(data.userBook?.code)
        row_date.setDetail(DateUtil.formatDate(data.userBook?.bookTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_24))
        row_seat.setDetail(data.userBook.numberSeat.toString())
        when (data.driverPost?.type) {
            Constant.CONVENIENT_TRIP -> {
                totalMoney = data.driverPost.regularPrice?.times(data.userBook.numberSeat!!)!!
                row_type_trip.setDetail("Tiện chuyến")
                row_one_seat_money.visible()
                row_one_seat_money.setDetail(NumberUtil.formatNumber(data.driverPost.regularPrice.toString()))
                row_money.setDetail(NumberUtil.formatNumber(totalMoney.toString()))
            }
            Constant.PRIVATE_TRIP -> {
                row_type_trip.setDetail("Xe riêng")
                row_one_seat_money.gone()
                row_money.setDetail(NumberUtil.formatNumber(data.driverPost.privatePrice2.toString()))
            }
        }
        if (data.driverPost?.highWay == 1) {
            row_way.visible()
        } else {
            row_way.gone()
        }
        tv_phone_driver.text = data.driver?.phone
        row_name_driver.setDetail(data.driver?.fullName)
        tv_detail_name_car.text = data.driverCar?.fullName
        row_license_plate.setDetail(data.driverCar?.licensePlate)
        when (data.userBook.status) {
            Constant.USER_BOOK_PENDING -> {

            }
            Constant.USER_BOOK_DONE -> {
                btn_status.text = "Hủy chuyến đi"
            }
            Constant.USER_BOOK_FINISH -> {
                btn_status.text = "Chuyến đi đã kết thúc"
                hideInfo()
            }
            Constant.USER_BOOK_CANCEL -> {
                btn_status.text = "Khách hủy chuyến"
                hideInfo()
            }
        }
        when (data.driverPost?.status) {
            Constant.DRIVER_POST_PENDING -> {
            }
            Constant.DRIVER_POST_PUBLISHED -> {
            }
            Constant.USER_BOOK_FINISH -> {
                hideInfo()
            }
            Constant.USER_BOOK_CANCEL -> {
                btn_status.text = "Tài xế hủy chuyến"
                hideInfo()
            }
        }
    }

    override fun userCancelUserRequestSuccess(msg: String) {
        toast(msg)
        mDialogStepTwo?.dismiss()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun userCancelUserRequestError() {
        toast(resources.getString(R.string.user_request_detail_cancel_booking_error))
    }


    private fun hideInfo() {
        row_phone_driver.gone()
        row_license_plate.gone()
    }
}