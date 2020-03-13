package com.example.anothertimdatxe.sprinthome.updateprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerGenderAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.soict.hoangviet.baseproject.extension.toast
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.util.*

class UpdateProfileActivity : BaseActivity<UpdateProfilePresenter>(), UpdateProfileView {
    private var mUserProfile: UserData? = null
    private var mSpinnerGenderAdapter: SpinnerGenderAdapter? = null
    private var mListItem: ArrayList<String>? = null

    companion object {
        const val REQUEST_CODE_PERMISSION: Int = 1000
        const val REQUEST_CODE_SELECT_IMAGE_GALLERY = 1998
        const val USER_PROFILE = "user_profile"
    }

    override val layoutRes: Int
        get() = R.layout.activity_update_profile

    override fun getPresenter(): UpdateProfilePresenter {
        return UpdateProfilePresenterImpl(this)
    }

    override fun initView() {
        initGenderSpinner()
        setData()
        setToolbar()
        edt_dob.setOnClickListener {
            var datePickerDialog = DatePickerDialogWidget(this, object : DatePickerDialogWidget.OnSetDateSuccessListener {
                override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
                    val calendar = Calendar.getInstance()
                    val dateInCurrent = Calendar.getInstance(TimeZone.getDefault())
                    calendar.set(year, month, dayOfMonth)
                    if (calendar.time.after(dateInCurrent.time) || calendar.time.equals(dateInCurrent.time)) {
                        toast("Không được chọn ngày trong tương lai!")
                    } else {
                        var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
                        var month: Int = calendar.get(Calendar.MONTH)
                        var year: Int = calendar.get(Calendar.YEAR)
                        edt_dob.text = "$day/${month + 1}/$year"
                    }
                }
            }).showDatePickerDialog()
        }
        btn_choose_avatar.setOnClickListener {
            handlePermission()
        }
        btn_save.setOnClickListener {
            mUserProfile?.fullName = edt_name.text.toString()
            mUserProfile?.email = edt_email.text.toString()
            mUserProfile?.phone = edt_phone.text.toString()
            mUserProfile?.address = edt_address.text.toString()
            mUserProfile?.birthday = edt_dob.text.toString()
            mUserProfile?.description = edt_des.text.toString()
            when {
                sp_gender.selectedItemPosition == 0 -> {
                    mUserProfile?.gender = "2"
                }
                sp_gender.selectedItemPosition == 1 -> {
                    mUserProfile?.gender = "0"
                }
                sp_gender.selectedItemPosition == 2 -> {
                    mUserProfile?.gender = "1"
                }
            }
            mPresenter!!.updateUserProfile(mUserProfile!!)
        }

    }

    private fun initGenderSpinner() {
        mListItem = arrayListOf("Giới tính","Nam","Nữ")
        mSpinnerGenderAdapter = SpinnerGenderAdapter(this,mListItem!!)
        sp_gender.adapter = mSpinnerGenderAdapter
    }

    private fun handlePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        } else {
            selectImageGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImageGallery()
                } else {
                    toast(resources.getString(R.string.request_read_external_storeage))
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun selectImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeType = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_SELECT_IMAGE_GALLERY -> {
                    //[GET ABSOLUTE PATH OF IMAGE SELECTED]
                    val imageURI = data?.data
                    imv_avatar.let {
                        it.setAvatar(this, it, imageURI!!)
                    }
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(imageURI, filePathColumn, null, null, null, null)
                    cursor.moveToFirst()
                    var columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                    var imageSelected: String = cursor.getString(columnIndex)
                    mUserProfile?.avatar = imageSelected
                    mPresenter!!.setFilePart(imageSelected)
                    cursor.close()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setToolbar() {
        toolbar?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.background = resources.getDrawable(R.color.colorPrimary, null)
            } else {
                it.background = resources.getDrawable(R.color.colorPrimary)
            }
        }
        toolbarTitle?.let {
            it.text = resources.getString(R.string.user_update_profile_toolbar).toUpperCase()
        }
    }

    private fun setData() {
        mUserProfile = intent.getSerializableExtra(USER_PROFILE) as UserData
        imv_avatar.setAvatar(this, mUserProfile?.avatar)
        edt_name.text = mUserProfile?.fullName
        edt_email.text = mUserProfile?.email
        edt_phone.text = mUserProfile?.phone
        edt_dob.text = mUserProfile?.birthday
        when (mUserProfile?.gender) {
            "0" -> {
                sp_gender.setSelection(1)//Male
            }
            "1" -> {
                sp_gender.setSelection(2)//Female
            }
            else -> {
                sp_gender.setSelection(0)//Another
            }
        }
        edt_address.text = mUserProfile?.address
        edt_des.setText(mUserProfile?.description)
    }

    override fun onFullNameError() {
        edt_name.setError("Vui lòng nhập đầy đủ họ tên!")
    }

    override fun onAddressError() {
        edt_address.setError("Vui lòng nhập địa chỉ!")
    }

    override fun onDescriptionError() {
        edt_des.error = "Vui lòng nhập mô tả!"
        edt_des.requestFocus()
    }

    override fun onUpdateProfileError() {
        toast("Cập nhật dữ liệu không thành công!")
    }

    override fun backUserProfile() {
        toast("Cập nhật thành công!")
        finish()
    }
}