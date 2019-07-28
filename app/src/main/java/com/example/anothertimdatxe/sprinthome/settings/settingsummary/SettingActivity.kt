package com.example.anothertimdatxe.sprinthome.settings

import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import beetech.com.carbooking.sprinthome.settings.SettingPresenter
import beetech.com.carbooking.sprinthome.settings.SettingPresenterImpl
import beetech.com.carbooking.sprinthome.settings.SettingView
import beetech.com.carbooking.sprinthome.settings.adapter.SettingAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.sprinthome.condition.ConditionActivity
import com.example.anothertimdatxe.sprinthome.settings.faqs.FaqsActivity
import com.example.anothertimdatxe.sprinthome.version.VersionUpdateActivity
import com.example.anothertimdatxe.sprintlogin.changepassword.ChangePasswordActivity
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.DialogUtil
import kotlinx.android.synthetic.main.activity_settings.*

class SettingActivity : BaseActivity<SettingPresenter>(), SettingView {

    companion object {
        const val CONDITION_ACTIVITY: Int = 0
        const val CHANGE_LANGUAGE_ACTIVITY: Int = 1
        const val CHANGE_PASSWORD_ACTIVITY: Int = 2
        const val APPRICATE_APPS_ACTIVITY: Int = 3
        const val FAQS_ACTIVITY: Int = 4
        const val UPDATE_VERSION_ACTIVITY: Int = 5
    }

    private val mListener = object : BaseRvListener {
        override fun onItemClick(position: Int) {
            when (position) {
                SettingActivity.CONDITION_ACTIVITY -> {
                    startActivity(Intent(this@SettingActivity, ConditionActivity::class.java))
                }
                SettingActivity.CHANGE_LANGUAGE_ACTIVITY -> {
                    Toast.makeText(this@SettingActivity, R.string.setting_developing, LENGTH_SHORT).show()
                }
                SettingActivity.CHANGE_PASSWORD_ACTIVITY -> {
                    startActivity(Intent(this@SettingActivity, ChangePasswordActivity::class.java))
                }
                SettingActivity.APPRICATE_APPS_ACTIVITY -> {
                    Toast.makeText(this@SettingActivity, R.string.setting_developing, LENGTH_SHORT).show()
                }
                SettingActivity.FAQS_ACTIVITY -> {
                    startActivity(Intent(this@SettingActivity, FaqsActivity::class.java))
                }
                SettingActivity.UPDATE_VERSION_ACTIVITY -> {
                    startActivity(Intent(this@SettingActivity, VersionUpdateActivity::class.java))
                }
            }
        }
    }
    var mAdapter: SettingAdapter? = null

    override val layoutRes: Int
        get() = R.layout.activity_settings

    override fun getPresenter(): SettingPresenter {
        return SettingPresenterImpl(this)
    }

    override fun initView() {
        mAdapter = SettingAdapter(this, mListener)
        rv_settings.adapter = mAdapter
        rv_settings.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
        toolbarTitle?.let {
            it.text = getString(R.string.setting_title)
        }
        tv_logout.setOnClickListener {
            DialogUtil.showAlertDialogNoTitle(this, resources.getString(R.string.setting_logout_msg), false,
                    resources.getString(R.string.setting_logout_positive),resources.getString(R.string.setting_logout_negative),
                    object : DialogUtil.BaseAlertDialogListener{
                        override fun onPositiveClick(dialogInterface: DialogInterface) {
                            dialogInterface.dismiss()
                            logOut()
                        }

                        override fun onNegativeClick(dialogInterface: DialogInterface) {
                            dialogInterface.dismiss()
                        }

                    })
        }
    }

    private fun logOut() {
        CarBookingSharePreference.clearAllPreference()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}