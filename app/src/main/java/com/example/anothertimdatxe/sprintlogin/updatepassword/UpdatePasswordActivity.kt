package com.example.anothertimdatxe.sprintlogin.updatepassword

import android.app.AlertDialog
import android.os.CountDownTimer
import android.text.TextUtils
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxePhoneSmsBaseActivity
import com.example.anothertimdatxe.extension.isValidStrongPassword
import com.example.anothertimdatxe.sprintlogin.forgotpassword.ForgotActivity
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.DialogUtil
import com.example.anothertimdatxe.util.MyApp
import com.example.anothertimdatxe.util.PhoneSms
import com.example.anothertimdatxe.util.ToastUtil
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : TimdatxePhoneSmsBaseActivity<UpdatePasswordPresenter>(), UpdatePasswordView {

    private var mCheckDirectForgot: Int = -1
    private var mCheckEmailOrPhone: Int = -1
    private var mToken: String? = null
    private var mEmail: String? = null
    private var dialog: UpdateCompleteDialog? = null
    private var cTimer: CountDownTimer? = null
    private var oneMinute: Long = 15 * 1000
    private var mCheckTimerInProcess: Boolean = false

    companion object {
        const val FORGOT_PASSWORD_DIRECT: String = "forgot_password_direct"
        const val FORGOT_PASSWORD_PHONE_OR_EMAIL: String = "forgot_password_phone_or_email"
    }

    override val layoutRes: Int
        get() = R.layout.activity_update_password

    override fun getPresenter(): UpdatePasswordPresenter {
        return UpdatePasswordPresenterImpl(this)
    }

    override fun initView() {
        initData()
        //[BEGIN SEND SMS TO PHONE]
        sendTokenToUserPhone()
        //{END SEND SMS TO PHONE}
        btn_forgot_password.setOnClickListener {
            if (valadate()) {
                if (mCheckSendSmsSuccess) {
                    when (mCheckDirectForgot) {
                        MyApp.KEY_FORGOT_PASSWORD_USER -> {
                            if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
                                mTokenPhoneSms.confirmTokenPhoneSms(et_token)
                            } else {
                                changePasswordWithUser()
                            }
                        }
                        MyApp.KEY_FORGOT_PASSWORD_DRIVER -> {
                            if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
                                mTokenPhoneSms.confirmTokenPhoneSms(et_token)
                            } else {
                                changePasswordWithDriver()
                            }
                        }
                    }
                }
            }
        }
        btn_update_password_resend.setOnClickListener {
            if (!mCheckTimerInProcess) {
                runTimeSendToken(oneMinute)
                if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
                    mTokenPhoneSms.startPhoneNumberVerification(mUserPhoneNumber)
                } else {
                    when (mCheckDirectForgot) {
                        MyApp.KEY_FORGOT_PASSWORD_USER -> {
                            mPresenter!!.userResendToken(mEmail!!)
                        }
                        MyApp.KEY_FORGOT_PASSWORD_DRIVER -> {
                            mPresenter!!.driverResendToken(mEmail!!)
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        mCheckDirectForgot = intent.getIntExtra(FORGOT_PASSWORD_DIRECT, -1)
        mCheckEmailOrPhone = intent.getIntExtra(FORGOT_PASSWORD_PHONE_OR_EMAIL, -1)
        mToken = intent.getStringExtra(ForgotActivity.FORGOT_PASSWORD_TOKEN)
        mEmail = intent.getStringExtra(ForgotActivity.FORGOT_PASSWORD_EMAIL)
        mTokenPhoneSms.initDataPhoneSms()
    }

    override fun onSendSmsSuccess() {
        super.onSendSmsSuccess()
        showAlertDialogSendTokenToPhone()
    }

    override fun onSendSmsFailed() {
        super.onSendSmsFailed()
        ToastUtil.show("Vui lòng thử lại sau!")
    }

    override fun onVerifySmsSuccess() {
        when (mCheckDirectForgot) {
            MyApp.KEY_FORGOT_PASSWORD_USER -> {
                changePasswordWithUser()
            }
            MyApp.KEY_FORGOT_PASSWORD_DRIVER -> {
                changePasswordWithDriver()
            }
        }
    }

    override fun onVerifySmsFailed() {
        ToastUtil.show("Mã token không hợp lệ!")
    }

    override fun showCompleteDiaglog() {
        dialog?.show() ?: run {
            dialog = UpdateCompleteDialog(this)
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    }

    override fun showAlertDialogSendTokenSuccess() {
        var alertDialog: AlertDialog = DialogUtil.showMessageDialog(this, R.string.alert_message_token_to_email)
        alertDialog.show()
    }

    override fun showAlertDialogSendTokenToPhone() {
        var alertDialog: AlertDialog = DialogUtil.showMessageDialog(this, R.string.alert_message_token_to_phone)
        alertDialog.show()
    }

    override fun sendTokenToUserPhone() {
        if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
            mUserPhoneNumber = PhoneSms.formatPhoneNumber(mEmail.toString())
            mTokenPhoneSms.startPhoneNumberVerification(mUserPhoneNumber)
        }
    }

    override fun changePasswordWithUser() {
        if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
            mPresenter!!.userUpdatePassword(
                    mEmail!!,
                    mToken!!,
                    et_password.text.toString()
            )
        } else {
            mPresenter!!.userUpdatePassword(
                    mEmail!!,
                    et_token.text.toString(),
                    et_password.text.toString()
            )
        }
    }

    override fun changePasswordWithDriver() {
        if (mCheckEmailOrPhone == MyApp.KEY_FORGOT_CONFIRM_PHONE) {
            mPresenter!!.driverUpdatePassword(
                    mEmail!!,
                    mToken!!,
                    et_password.text.toString()
            )
        } else {
            mPresenter!!.driverUpdatePassword(
                    mEmail!!,
                    et_token.text.toString(),
                    et_password.text.toString()
            )
        }
    }

    override fun runTimeSendToken(totalTime: Long) {
        cTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mCheckTimerInProcess = true
                var timer: String = String.format("%02d", (millisUntilFinished / 1000))
                btn_update_password_resend.text = timer
            }

            override fun onFinish() {
                mCheckTimerInProcess = false
                btn_update_password_resend.text = "Re-send"
                cTimer!!.cancel()
            }

        }.start()
    }

    private fun valadate(): Boolean {
        if (TextUtils.isEmpty(et_password.text.toString())) {
            et_password.error = "Xin hãy nhập mật khẩu!"
            et_password.requestFocus()
            return false
        } else if (!et_password.text.toString().isValidStrongPassword()) {
            et_password.error = "Mật khẩu bao gồm ít nhất 8 ký tự, số, chữ hoa, chữ thường và kí tự đặc biệt!"
            et_password.requestFocus()
            return false
        } else if (!et_password.text.toString().equals(et_password_confirm.text.toString())) {
            et_password.error = "Mật khẩu xác nhận không đúng!"
            et_password.requestFocus()
            return false
        }
        return true
    }

    override fun goToLoginHome() {
        startActivityAndClearTask(LoginActivity::class.java)
    }


}