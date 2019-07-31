package com.example.anothertimdatxe.sprintlogin.updateinfo

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import kotlinx.android.synthetic.main.activity_update_infomation.*
import java.util.*

class UpdateInfoActivity : BaseActivity<UpdateInfoPresenter>(), UpdateInfoView, DatePickerDialogWidget.onSetDateSuccessListener {
    private var userData: UserData? = null
    private var mCalandar: Calendar? = null
    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        val dateInCurrent = Calendar.getInstance(TimeZone.getDefault())
        calendar.set(year, month - 1, dayOfMonth + 1)
        if (calendar.time.after(dateInCurrent.time)) {
            onDateError()
        } else {
            tv_day.text = dayOfMonth.toString()
            tv_month.text = month.toString()
            tv_year.text = year.toString()
        }
    }

    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    override val layoutRes: Int
        get() = R.layout.activity_update_infomation

    override fun getPresenter(): UpdateInfoPresenter {
        return UpdateInfoPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        initData()
        initDatePicker()
        initListener()
    }

    private fun initData() {
        userData = CarBookingSharePreference.getUserData()
        mCalandar = Calendar.getInstance()
        edt_name.setText(userData?.full_name)
        edt_email.setText(userData?.email)
        edt_phone.setText(userData?.phone)
        if (userData?.birthday.isNullOrEmpty()) {
            tv_day.text = mCalandar?.get(Calendar.DAY_OF_MONTH).toString()
            tv_month.text = (mCalandar?.get(Calendar.MONTH)!!.plus(1)).toString()
            tv_year.text = mCalandar?.get(Calendar.YEAR).toString()
        } else {
            val mList = userData?.birthday!!.split("-")
            tv_year.text = mList[0]
            tv_month.text = mList[1]
            tv_day.text = mList[2]
        }
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.update_info_toolbar_title).toUpperCase()
        }
    }

    private fun initDatePicker() {
        mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
    }

    private fun initListener() {
        tv_day.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        tv_month.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        tv_year.setOnClickListener {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        btn_login.setOnClickListener {
            userData?.full_name = edt_name.text.toString()
            userData?.birthday = "${tv_day.text}/${tv_month.text}/${tv_year.text}"
            userData?.gender = sp_gender.selectedItemPosition.toString()
            if (cb_condition.isChecked) {
                mPresenter!!.updateInfo(userData!!)
            } else {
                onCheckBoxError()
            }
        }
    }

    override fun onFullNameError() {
        edt_name.error = "Vui lòng nhập họ tên!"
        edt_name.requestFocus()
    }

    override fun onDateError() {
        ToastUtil.show("Không chọn thời gian trong tương lai!")
    }

    override fun onCheckBoxError() {
        ToastUtil.show("Vui lòng đồng ý với các điều khoản của chúng tôi!")
    }

    override fun onUpdateSuccess() {
        startActivityAndClearTask(HomeActivity::class.java)
    }

}