package com.example.anothertimdatxe.sprinthome.help

import android.os.Build
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.ContactEntity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.toolbar.*

class SupportActivity : BaseActivity<SupportPresenter>(), SupportView {
    override val layoutRes: Int
        get() = R.layout.activity_support

    override fun getPresenter(): SupportPresenter {
        return SupportPresenterImpl(this)
    }

    override fun initView() {
        setToolbar()
        setBaseData()
        btn_send.setOnClickListener {
            val data = ContactEntity()
            data.address = tv_address.text?.toString() ?: ""
            data.phone = tv_phone.text?.toString() ?: ""
            data.email = tv_email.text?.toString() ?: ""
            data.content = edt_content?.text.toString()
            mPresenter!!.sendSupport(data)
        }
    }

    private fun setToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leftbutton?.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp, null))
        } else {
            leftbutton?.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp))
        }
        toolbar_title?.let {
            it.text = resources.getString(R.string.toolbar).toUpperCase()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar?.background = resources.getDrawable(R.color.colorPrimary, null)
        } else {
            toolbar?.background = resources.getDrawable(R.color.colorPrimary)
        }
    }

    private fun setBaseData() {
        val userData = CarBookingSharePreference.getUserData()
        tv_phone.setText(userData?.phone)
        tv_email.setText(userData?.email)
        userData?.address?.let {
            tv_address.setText(it)
        }
    }

    override fun onPhoneError() {
        tv_phone.error = "Vui lòng nhập số điện thoại!"
        tv_phone.requestFocus()
    }

    override fun onEmailError() {
        tv_email.error = "Vui lòng nhập email!"
        tv_email.requestFocus()
    }

    override fun onAddressError() {
        tv_address.error = "Vui lòng nhập địa chỉ!"
        tv_address.requestFocus()
    }

    override fun onEdittextError() {
        edt_content.error = "Vui lòng nhập nội dung"
        edt_content.requestFocus()
    }

    override fun showContactSuccess() {
        ToastUtil.show(resources.getString(R.string.support_success))
        finish()
    }

    override fun showContactFail() {
        ToastUtil.show(resources.getString(R.string.support_fail))
    }

}