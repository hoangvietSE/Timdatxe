package com.example.anothertimdatxe.sprintlogin.login

import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.Window
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.anothertimdatxe.sprintlogin.forgotpassword.ForgotActivity
import com.example.anothertimdatxe.sprintlogin.register.RegisterActivity
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_direct_login.*
import kotlinx.android.synthetic.main.dialog_direct_register.*

class LoginActivity : TimdatxeBaseActivity<LoginPresenter>(), LoginView, LoginSocial.LoginSocialListener {
    private var mLoginSocial = LoginSocial(this, this)

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
        btn_regis.setOnClickListener {
            showDialogConfirmDirectRegister()
        }
        btn_forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
        btn_login_fb.setOnClickListener { mLoginSocial.loginFacebook() }
        btn_login_google.setOnClickListener { mLoginSocial.loginGoogle() }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginManager.getInstance().logOut()
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

    private fun showDialogConfirmDirectRegister() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_direct_register)
        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawableResource(R.drawable.bg_direct_register)
        dialog.show()
        dialog.tvCancelRegister.setOnClickListener {
            dialog.dismiss()
        }
        dialog.tv_driver_register_direct.setOnClickListener {
            dialog.dismiss()
            goToRegis(true)
        }
        dialog.tv_user_register_direct.setOnClickListener {
            dialog.dismiss()
            goToRegis(false)
        }
    }

    private fun showDialogConfirmDirectLogin() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_direct_login)
        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawableResource(R.drawable.bg_direct_register)
        //dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.tvCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.tv_user_login_direct.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            mPresenter!!.loginUser(et_email.getText().toString(), et_password.getText().toString())
        })
        dialog.tv_driver_login_direct.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            mPresenter!!.loginDriver(et_email.getText().toString(), et_password.getText().toString())
        })
        dialog.show()
    }

    override fun goToNextScreen() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun goToRegis(key_register: Boolean) {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            putExtra(RegisterActivity.KEY_REGISTER, key_register)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mLoginSocial.onActivityResult(requestCode, resultCode, data)
    }

    override fun onVerifyLoginSocialSuccess(socialId: String, full_name: String, email: String, socialType: String) {
        mPresenter!!.loginSocial(socialId, full_name, email, socialType)
    }
}