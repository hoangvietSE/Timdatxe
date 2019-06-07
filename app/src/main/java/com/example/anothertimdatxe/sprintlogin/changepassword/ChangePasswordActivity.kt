package beetech.com.carbooking.sprintlogin.changepassword

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : TimdatxeBaseActivity<ChangePasswordPresenter>(), ChangePasswordView {
    override val layoutRes: Int
        get() = R.layout.activity_change_password

    override fun getPresenter(): ChangePasswordPresenter {
        return ChangePasswordActivityImpl(this)
    }

    override fun initView() {
        tv_save_change_password.setOnClickListener(View.OnClickListener {
            if (isValidate()) {
                mPresenter!!.userChangePassword(
                    et_change_password_old.getText().toString(),
                    et_change_password_new.getText().toString()
                )
            }
        })
        toolbarTitle?.let {
            it.text = getString(R.string.toolbar_title_change_password)
        }
    }

    private fun isValidate(): Boolean {
        if (TextUtils.isEmpty(et_change_password_old.text)
            || TextUtils.isEmpty(et_change_password_new.text)
            || TextUtils.isEmpty(et_change_password_retype.text)
        ) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", LENGTH_LONG).show()
            return false
        } else if (et_change_password_old.getText().toString().length < 6
            || et_change_password_new.getText().toString().length < 6
            || et_change_password_new.getText().toString().length < 6
        ) {
            Toast.makeText(this, "Hãy nhập mật khẩu hợp lệ", LENGTH_LONG).show()
            return false
        } else if (!(et_change_password_new.text.toString().equals(et_change_password_retype.text.toString()))) {
            Toast.makeText(this, "Mật khẩu nhập lại không chính xác", LENGTH_LONG).show()
            return false
        }
        return true
    }

}