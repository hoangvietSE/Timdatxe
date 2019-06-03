package com.example.anothertimdatxe.sprintlogin.forgotpassword

import android.content.Intent
import com.example.anothertimdatxe.R
import android.text.TextUtils
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.sprintlogin.updatepassword.UpdatePasswordActivity
import com.example.anothertimdatxe.util.DialogUtil
import com.example.anothertimdatxe.util.MyApp
import kotlinx.android.synthetic.main.activity_forgot.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*

class ForgotActivity : TimdatxeBaseActivity<ForgotPresenter>(), ForgotView {

    private var mCheckDirectForgot: Int = -1 //User or Driver
    private var mCheckPhoneOrEmail: Int = -1 //enter phone or email

    companion object {
        const val FORGOT_PASSWORD_TOKEN = "forgot_password_token" //token from calling api
        const val FORGOT_PASSWORD_EMAIL = "forgot_password_email" //email entered
    }

    override val layoutRes: Int
        get() = R.layout.activity_forgot

    override fun getPresenter(): ForgotPresenter {
        return ForgotPresenterImpl(this)
    }

    override fun initView() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.tv_forgot_toolbar_title)
        }
        btn_forgot_password.setOnClickListener {
            if (isValidate()) {
                checkInputFormat(et_forgot_email.text.toString())
                showDialogConfirmDirectForgot()
            }
        }
    }

    override fun goToUpdate(token: String?) {
        startActivity(Intent(this, UpdatePasswordActivity::class.java).apply {
            putExtra(FORGOT_PASSWORD_TOKEN, token)
            putExtra(FORGOT_PASSWORD_EMAIL, et_forgot_email.text.toString())
            putExtra(UpdatePasswordActivity.FORGOT_PASSWORD_DIRECT, mCheckDirectForgot)
            putExtra(UpdatePasswordActivity.FORGOT_PASSWORD_PHONE_OR_EMAIL, mCheckPhoneOrEmail)
        })
    }

    override fun showDialogConfirmDirectForgot() {
        var dialog = DialogUtil.showConfirmDiaglogNotCancel(this, R.layout.dialog_forgot_password, R.color.color_white)
        dialog.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btn_forgot_user.setOnClickListener {
            mCheckDirectForgot = MyApp.KEY_FORGOT_PASSWORD_USER
            mPresenter!!.onUserForgotPassword(et_forgot_email.text.toString())
            dialog.dismiss()
        }
        dialog.btn_forgot_driver.setOnClickListener {
            mCheckDirectForgot = MyApp.KEY_FORGOT_PASSWORD_DRIVER
            mPresenter!!.onDriverForgotPassword(et_forgot_email.text.toString())
            dialog.dismiss()
        }
    }

    private fun isValidate(): Boolean {
        if (TextUtils.isEmpty(et_forgot_email.text)) {
            et_forgot_email.error = "Vui lòng nhập số điện thoại hoặc địa chỉ Email!"
            et_forgot_email.requestFocus()
            return false
        } else if (!et_forgot_email.text.toString().isValidEmail()) {
            if (!et_forgot_email.text.toString().isValidPhone()) {
                et_forgot_email.error = "Vui lòng nhập đúng định dạng!"
                et_forgot_email.requestFocus()
                return false
            }
        }
        return true
    }

    private fun checkInputFormat(email: String) {
        if (email.isValidEmail()) {
            mCheckPhoneOrEmail = MyApp.KEY_FORGOT_CONFIRM_EMAIL
        } else if (email.isValidPhone()) {
            mCheckPhoneOrEmail = MyApp.KEY_FORGOT_CONFIRM_PHONE
        }
    }
}