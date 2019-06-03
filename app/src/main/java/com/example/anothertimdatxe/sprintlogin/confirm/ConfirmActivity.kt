package com.example.anothertimdatxe.sprintlogin.confirm

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.common.TimdatxePhoneSmsBaseActivity
import com.example.anothertimdatxe.sprintlogin.register.RegisterActivity
import com.example.anothertimdatxe.util.PhoneSms
import android.text.TextUtils
import com.example.anothertimdatxe.request.ActiveRequest
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.anothertimdatxe.util.MyApp
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : TimdatxePhoneSmsBaseActivity<ConfirmPresenter>(), ConfirmView {

    private var checkRegister: Int = -1 //1 is Driver, 0 is User
    private var tokenFromServer: String = ""
    private var emailFromServer: String = "" //PhoneNumber

    companion object {
        const val KEY_USER_PHONE_NUMBER = "key_user_phone_number"
    }

    override val layoutRes: Int
        get() = R.layout.activity_confirm

    override fun getPresenter(): ConfirmPresenter {
        return ConfirmPresenterImpl(this)
    }

    override fun initView() {
        initData()
        btn_confirm.setOnClickListener {
            if (validateFromToken()) {
                showLoading()
                mTokenPhoneSms.confirmTokenPhoneSms(otp_view)
            }
        }
        btn_resend_otp.setOnClickListener {
            configResendSms()
        }
        //send SMS otp code
        configSendSms()
    }

    private fun initData() {
        checkRegister = intent.getIntExtra(RegisterActivity.KEY_REGISTER, -1)
        tokenFromServer = intent.getStringExtra(RegisterActivity.REGISTER_TOKEN)
        emailFromServer = PhoneSms.formatOriginalPhoneNumber(intent.getStringExtra(RegisterActivity.REGISTER_PHONE))//09....
        mUserPhoneNumber = intent.getStringExtra(KEY_USER_PHONE_NUMBER)//+84
        mTokenPhoneSms.initDataPhoneSms()
    }

    override fun configSendSms() {
        mTokenPhoneSms.startPhoneNumberVerification(mUserPhoneNumber)
    }

    override fun configResendSms() {
        mTokenPhoneSms.resendPhoneNumberVerification(mUserPhoneNumber)
    }

    override fun onVerifySmsSuccess() {
        when (checkRegister) {
            MyApp.KEY_REGISTER_DRIVER -> {
                mPresenter!!.onActiveDriver(ActiveRequest(
                        emailFromServer,
                        tokenFromServer
                ))
            }
            MyApp.KEY_REGISTER_USER -> {

            }
        }
    }

    override fun onVerifySmsFailed() {
        hideLoading()
        otp_view.error = "Mã OTP không chính xác"

    }

    override fun goToScreenHome() {
        startActivityAndClearTask(this, HomeActivity::class.java)
    }

    override fun validateFromToken(): Boolean {
        if (TextUtils.isEmpty(otp_view.toString()) || otp_view.text.toString().length == 0) {
            otp_view.error = "Hãy nhập mã OTP"
            return false
        } else if (otp_view.text.toString().length < 6) {
            otp_view.error = "Mã OTP phải nhập đủ 6 kí tự"
            return false
        }else{
            return true
        }
    }
}