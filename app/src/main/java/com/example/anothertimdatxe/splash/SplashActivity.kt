package com.example.anothertimdatxe.splash

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.customview.CarBookingLoading
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.DialogUtil

class SplashActivity : AppCompatActivity(), SplashView {
    private var mCarBookingLoading: CarBookingLoading? = null
    private var mPresenter: SplashPresenter? = null

    companion object {
        const val SPLASH_TIME = 500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCarBookingLoading = CarBookingLoading.getInstance(this)
        mPresenter = SplashPresenterImpl(this)
        mPresenter?.refreshToken()
    }

    override fun goToLoginScreen() {
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }

    override fun goToUpdateInfoScreen() {
        //do-something
    }

    override fun goToHomeScreen() {
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }

    override fun showNoConnectedToNetword() {
        DialogUtil.showAlertDialogNoNegative(
                this,
                resources.getString(R.string.dialog_no_connectivity_title),
                resources.getString(R.string.dialog_no_connectivity_msg),
                false,
                resources.getString(R.string.dialog_no_connectivity_positive),
                object : DialogUtil.BaseAlertDialogListener{
                    override fun onPositiveClick(dialogInterface: DialogInterface) {
                        dialogInterface.dismiss()
                        finish()
                    }

                    override fun onNegativeClick(dialogInterface: DialogInterface) {
                        //no-op
                    }

                }

        )
    }

    override fun showDialogExpiredSessionLogin() {
        DialogUtil.showAlertDialogTitle(
                this,
                resources.getString(R.string.dialog_expired_session_title),
                resources.getString(R.string.dialog_expired_session_msg),
                false,
                resources.getString(R.string.dialog_expired_session_positive),
                resources.getString(R.string.dialog_expired_session_negative),
                object : DialogUtil.BaseAlertDialogListener{
                    override fun onPositiveClick(dialogInterface: DialogInterface) {
                        goToLoginScreen()
                    }

                    override fun onNegativeClick(dialogInterface: DialogInterface) {
                        finish()
                    }

                }
        )
    }

    override fun refreshTokenError(message:String) {
        DialogUtil.showAlertDialogNoNegative(
                this,
                resources.getString(R.string.dialog_expired_session_title),
                message,
                false,
                resources.getString(R.string.dialog_refresh_token_error_positive),
                object : DialogUtil.BaseAlertDialogListener{
                    override fun onPositiveClick(dialogInterface: DialogInterface) {
                        finish()
                    }

                    override fun onNegativeClick(dialogInterface: DialogInterface) {
                        //no-op
                    }

                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.destroy()
    }

    override fun showLoading() {
        mCarBookingLoading?.show()
    }

    override fun hideLoading() {
        mCarBookingLoading?.hide()
    }
}