package com.example.anothertimdatxe.sprintlogin.login

import android.app.Dialog
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidPhone
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_direct_login.*

class LoginActivity : TimdatxeBaseActivity<LoginPresenter>(), LoginView {
    override fun goToNextScreen() {
        
    }

    override fun getPresenter(): LoginPresenter {
        //do-something
        return LoginPresenterImpl(this)
    }

    override val layoutRes: Int
        get() = R.layout.activity_login


    override public fun initView() {
        btn_login.setOnClickListener(View.OnClickListener {
            if (validate()) {
                showDialogConfirmDirectLogin()
            }
        })
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(et_email!!.getText().toString())) {
            et_email!!.error = "Please enter email"
            et_email!!.requestFocus()
            return false
        } else if (!(et_email!!.getText().toString().isValidEmail() || et_email!!.getText().toString().isValidPhone())) {
            et_email!!.error = "Email or Phone isn't valid"
            et_email!!.requestFocus()
            return false
        } else if (TextUtils.isEmpty(et_password!!.getText().toString())) {
            et_password!!.error = "Please enter password"
            et_password!!.requestFocus()
            return false
        } else if (et_password!!.getText().toString().length <= 6) {
            et_password!!.error = "Password must be greater than 6"
            et_password!!.requestFocus()
            return false
        }
        return true
    }

    private fun showDialogConfirmDirectLogin() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_direct_login)
        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawableResource(R.drawable.bg_direct_register)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.tvCancelLogin.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.tvLoginUser.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            mPresenter!!.loginUser(et_email.getText().toString(), et_password.getText().toString())
        })
        dialog.tvLoginDriver.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            mPresenter!!.loginDriver(et_email.getText().toString(), et_password.getText().toString())
        })
        dialog.show()
    }
}