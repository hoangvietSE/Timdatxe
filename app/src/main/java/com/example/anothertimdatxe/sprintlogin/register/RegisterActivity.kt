package com.example.anothertimdatxe.sprintlogin.register

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.extension.isValidEmail
import com.example.anothertimdatxe.extension.isValidPhone
import com.example.anothertimdatxe.extension.isValidStrongPassword
import com.example.anothertimdatxe.request.RegisterRequest
import com.example.anothertimdatxe.sprintlogin.confirm.ConfirmActivity
import com.example.anothertimdatxe.util.PhoneSms
import kotlinx.android.synthetic.main.acitivity_register.*
import kotlinx.android.synthetic.main.toolbar.*

class RegisterActivity : TimdatxeBaseActivity<RegisterPresenter>(), RegisterView {
    private var checkRegister: Boolean = false
    private var userPhoneNumber: String = ""

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val REGISTER_TOKEN = "register_token"
        const val REGISTER_PHONE = "register_phone"
        const val IS_FROM_REGIS = "is_from_regis"
        const val KEY_REGISTER = "key_register"
        const val NOTICATION_ID = 888
    }

    override val layoutRes: Int
        get() = R.layout.acitivity_register

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenterImpl(this)
    }

    override fun initView() {
        initData()
        toolbar_title.text = getString(R.string.register_title)
        btn_register.let {
            it.setOnClickListener {
                if (isValidate()) {
                    if (checkRegister) {
                        //DRIVER
                        userPhoneNumber = PhoneSms.formatPhoneNumber(et_phone.text.toString())
                        mPresenter!!.registerDriver(
                                RegisterRequest(
                                        et_name.text.toString(),
                                        et_regis_email.text.toString(),
                                        et_regis_password.text.toString(),
                                        PhoneSms.formatOriginalPhoneNumber(userPhoneNumber)
                                )
                        )
                    } else {
                        //USER
                    }
                }
            }
        }
        initListener()
    }

    override fun goToConfirm(token_register: String, checkRegister: Int) {
        sendNotification(token_register)
        startActivity(Intent(this, ConfirmActivity::class.java).apply {
            putExtra(KEY_REGISTER, checkRegister)
            putExtra(REGISTER_TOKEN, token_register)
            putExtra(IS_FROM_REGIS, true)
            putExtra(REGISTER_PHONE, PhoneSms.formatPhoneNumber(et_phone.text.toString()))
            putExtra(ConfirmActivity.KEY_USER_PHONE_NUMBER, userPhoneNumber)
        })
    }

    private fun sendNotification(token_register: String) {
        var intent = Intent(this, ConfirmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(REGISTER_TOKEN, token_register)
        }
        var pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)
        var defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notificationCompat: NotificationCompat.Builder = NotificationCompat.Builder(this, "CHANNEL ID")
                .setSmallIcon(R.drawable.ic_avatar)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar))
                .setContentTitle("Tìm đặt xe")
                .setContentText("Mã xác nhận của bạn là $token_register")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
        var notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Since API 26+, notification channel is needed
        lateinit var channel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(CHANNEL_ID, "Timdatxe", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationCompat.build())
    }

    private fun initData() {
        checkRegister = intent.getBooleanExtra(KEY_REGISTER, false)
    }

    private fun initListener() {
        tvTerm.let {
            it.setOnClickListener {
                cbRegister.isChecked = !cbRegister.isChecked
            }
        }
    }

    private fun isValidate(): Boolean {
        if (TextUtils.isEmpty(et_name.text.toString())) {
            et_name.error = "Bạn chưa nhập họ tên"
            et_name.requestFocus()
            return false
        } else if (TextUtils.isEmpty(et_regis_email.text.toString()) || !et_regis_email.text.toString().isValidEmail()) {
            et_regis_email.error = "Email nhập không đúng định dạng"
            et_regis_email.requestFocus()
            return false
        } else if (TextUtils.isEmpty(et_regis_password.text.toString()) || !et_regis_password.text.toString().isValidStrongPassword()) {
            et_regis_password.error = "Password nhập không đúng định dạng"
            et_regis_password.requestFocus()
            return false
        } else if (!et_regis_password.text.toString().equals(et_password_confirm.text.toString())) {
            et_password_confirm.error = "Mật khẩu không trùng khớp"
            et_password_confirm.requestFocus()
            return false
        } else if (TextUtils.isEmpty(et_phone.text.toString()) || !et_phone.text.toString().isValidPhone()) {
            et_phone.error = "Số điện thoại không đúng định dạng"
            et_phone.requestFocus()
            return false
        } else if (!cbRegister.isChecked) {
            cbRegister.error = "Hãy đồng ý với các điều khoản của chúng tôi"
            cbRegister.requestFocus()
            return false
        } else if (et_name.text.toString().length > 50) {
            et_name.error = "Họ và tên không vượt quá 50 kí tự"
            et_name.requestFocus()
            return false
        } else if (et_regis_email.text.toString().length > 150) {
            et_regis_email.error = "Email không vượt quá 150 kí tự"
            et_regis_email.requestFocus()
            return false
        }
        return true
    }
}