package com.example.anothertimdatxe.sprintlogin.changepassword

import android.text.TextUtils
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidStrongPassword
import com.example.anothertimdatxe.widget.TextWatcherPassword
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : TimdatxeBaseActivity<ChangePasswordPresenter>(), ChangePasswordView {
    override val layoutRes: Int
        get() = R.layout.activity_change_password

    override fun getPresenter(): ChangePasswordPresenter {
        return ChangePasswordActivityImpl(this)
    }

    override fun initView() {
        tv_save_change_password.setOnClickListener {
            if (isValidate()) {
                mPresenter!!.userChangePassword(
                        et_change_password_old.getText().toString(),
                        et_change_password_new.getText().toString()
                )
            }
        }
        toolbarTitle?.let {
            it.text = getString(R.string.toolbar_title_change_password)
        }
        addingTextWatcher()
    }

    private fun addingTextWatcher() {
        et_change_password_old.addTextChangedListener(TextWatcherPassword(til_old_password))
        et_change_password_new.addTextChangedListener(TextWatcherPassword(til_new_password))
        et_change_password_retype.addTextChangedListener(TextWatcherPassword(til_confirm_password))
    }

    private fun isValidate(): Boolean {
        if (TextUtils.isEmpty(et_change_password_old.text)) {
            til_old_password.error = getString(R.string.change_password_old_no_type)
            return false
        } else if (!et_change_password_old.text.toString().isValidStrongPassword()) {
            til_old_password.error = getString(R.string.change_password_old_error_type)
            return false
        } else if (TextUtils.isEmpty(et_change_password_new.text)) {
            til_new_password.error = getString(R.string.change_password_new_no_type)
            return false
        } else if (!et_change_password_new.text.toString().isValidStrongPassword()) {
            til_new_password.error = getString(R.string.change_password_new_error_type)
            return false
        } else if (TextUtils.isEmpty(et_change_password_retype.text)) {
            til_confirm_password.error = getString(R.string.change_password_retype_no_type)
            return false
        } else if (!(et_change_password_new.text.toString().equals(et_change_password_retype.text.toString()))) {
            til_confirm_password.error = getString(R.string.change_password_not_exactly)
            return false
        }
        return true
    }

}