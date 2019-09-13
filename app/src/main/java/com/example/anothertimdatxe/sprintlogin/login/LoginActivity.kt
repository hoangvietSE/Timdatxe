package com.example.anothertimdatxe.sprintlogin.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.introduce.IntroduceActivity
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.anothertimdatxe.sprintlogin.forgotpassword.ForgotActivity
import com.example.anothertimdatxe.sprintlogin.register.RegisterActivity
import com.example.anothertimdatxe.sprintlogin.updateinfo.UpdateInfoActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.widget.TextWatcherPassword
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_direct_register.*
import kotlinx.android.synthetic.main.dialog_login.*

class LoginActivity : TimdatxeBaseActivity<LoginPresenter>(), LoginView, LoginSocial.LoginSocialListener {
    private var mLoginSocial = LoginSocial(this, this)

    override fun getPresenter(): LoginPresenter {
        //do-something
        return LoginPresenterImpl(this)
    }

    override val layoutRes: Int
        get() = R.layout.activity_login


    override public fun initView() {
        btn_login.setOnClickListener {
            if (validate()) {
                showDialogConfirmDirectLogin()
                return@setOnClickListener
            }
        }
        btn_regis.setOnClickListener {
            showDialogConfirmDirectRegister()
        }
        btn_forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
        btn_login_fb.setOnClickListener { mLoginSocial.loginFacebook() }
        btn_login_google.setOnClickListener { mLoginSocial.loginGoogle() }
        et_password.addTextChangedListener(TextWatcherPassword(input_password))
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginManager.getInstance().logOut()
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(et_email.text)) {
            et_email!!.error = "Vui lòng nhập số điện thoại hoặc địa chỉ email!"
            et_email!!.requestFocus()
            return false
        } else if (!(et_email!!.getText().toString().isValidEmail() || et_email!!.getText().toString().isValidPhone())) {
            et_email!!.error = "Vui lòng nhập số điện thoại hoặc địa chỉ email đúng định dạng!"
            et_email!!.requestFocus()
            return false
        } else if (TextUtils.isEmpty(et_password!!.getText().toString())) {
            input_password!!.error = "Vui lòng nhập mật khẩu!"
            return false
        } else if (et_password!!.getText().toString().length <= 6) {
            input_password!!.error = "Mật khẩu phải lớn hơn 6 kí tự bao gồm chữ cái, số và kí tự đặc biệt!"
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
//        LoginDialogFrag().show(supportFragmentManager,"demo")
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_login)
        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog.window!!.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.iv_icon.setOnClickListener {
            dialog.dismiss()
        }
        dialog.tv_user_login_direct.setOnClickListener {
            dialog.dismiss()
            mPresenter!!.loginUser(et_email.getText().toString(), et_password.getText().toString())
        }
        dialog.tv_driver_login_direct.setOnClickListener {
            dialog.dismiss()
            mPresenter!!.loginDriver(et_email.getText().toString(), et_password.getText().toString())
        }
//        setUpViewBase(dialog)
//        dialog.tv_title.visibility = View.GONE
        dialog.show()
//        PrettyDialog(this)
////                .setTitle("PrettyDialog Title")
////                .setMessage("PrettyDialog Message")
//                .show();
    }

    override fun goToNextScreen() {
        if (CarBookingSharePreference.getUserData()?.isDriver!!) {
            if (CarBookingSharePreference.getWelcomeDriverApp()) {
                startActivityAndClearTask(IntroduceActivity::class.java)
                finish()
                return
            }
        } else {
            if (CarBookingSharePreference.getWelcomeUserApp()) {
                startActivityAndClearTask(IntroduceActivity::class.java)
                finish()
                return
            }
        }
        startActivityAndClearTask(HomeActivity::class.java)
        finish()
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

    override fun goToUpdateInfo() {
        startActivityAndClearTask(UpdateInfoActivity::class.java)
    }

    fun setUpViewBase(dialog: Dialog) {
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        lp.setMargins(0, resources.getDimensionPixelSize(libs.mjn.prettydialog.R.dimen.pdlg_icon_size) / 2, 0, 0)
        dialog.ll_content.layoutParams = lp
//        dialog.ll_content.setPadding(0, (1.25 * resources.getDimensionPixelSize(libs.mjn.prettydialog.R.dimen.pdlg_icon_size) / 2).toInt(), 0, resources.getDimensionPixelSize(libs.mjn.prettydialog.R.dimen.pdlg_space_1_0x))
//        dialog.tv_title.visibility = View.GONE
//        close_rotation_animation = RotateAnimation(0f, 180f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f)
//        close_rotation_animation.setDuration(300)
//        close_rotation_animation.setRepeatCount(Animation.ABSOLUTE)
//        close_rotation_animation.setInterpolator(DecelerateInterpolator())
//        close_rotation_animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {
//
//            }
//
//            override fun onAnimationEnd(animation: Animation) {
//                thisDialog.dismiss()
//            }
//
//            override fun onAnimationRepeat(animation: Animation) {
//
//            }
//        })
//
//        iv_icon.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    v.alpha = 0.7f
//                    true
//                }
//                MotionEvent.ACTION_UP -> {
//                    v.alpha = 1.0f
//                    if (icon_animation) {
//                        v.startAnimation(close_rotation_animation)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
    }
}