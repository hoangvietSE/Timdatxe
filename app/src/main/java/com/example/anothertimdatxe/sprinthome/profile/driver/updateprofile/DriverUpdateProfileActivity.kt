package com.example.anothertimdatxe.sprinthome.profile.driver.updateprofile

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnerGenderAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.DriverProfileResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.setlicenseImage
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.DialogUtil
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import kotlinx.android.synthetic.main.activity_driver_update_profile.*
import java.util.*

class DriverUpdateProfileActivity : BaseActivity<DriverUpdateProfilePresenter>(), DriverUpdateProfileView,
        DatePickerDialogWidget.OnSetDateSuccessListener {
    private var data: DriverProfileResponse? = null
    private var mListGender: ArrayList<String>? = null
    private var mSpinnerGenderAdapter: SpinnerGenderAdapter? = null
    private var mDatePickerDialogWidget = DatePickerDialogWidget(this, this)
    private var onEditChoose: Boolean = false
    private var mapBefore: MutableMap<String?, Uri?> = mutableMapOf()
    private var mapAfter: MutableMap<String?, Uri?> = mutableMapOf()


    companion object {
        const val DRIVER_PROFILE = "driver_profile"
        const val PERMISSION_REQUEST_CODE_AVATAR = 1000
        const val PERMISSION_REQUEST_CODE_LICENSE_BEFORE = 1001
        const val PERMISSION_REQUEST_CODE_LICENSE_AFTER = 1002
        const val SELECT_IMAGE_FROM_ALBUM_AVATAR = 1998
        const val SELECT_IMAGE_FROM_ALBUM_LICENSE_BEFORE = 1999
        const val SELECT_IMAGE_FROM_ALBUM_LICENSE_AFTER = 2000
    }

    override val layoutRes: Int
        get() = R.layout.activity_driver_update_profile

    override fun getPresenter(): DriverUpdateProfilePresenter {
        return DriverUpdateProfilePresenterImpl(this)
    }

    override fun initView() {
        initGenderAdapter()
        getDataIntent()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.driver_update_profile_toolbar_title)
        }
    }

    override fun setListener() {
        imv_edit.setOnClickListener {
            hideEditButton()
            showTickButton()
            showCloseButton()
            showCameraButton()
        }
        imv_tick_done.setOnClickListener {
            showEditButton()
            hideTickButton()
            hideCloseButton()
            hideCameraButton()
        }
        imv_close.setOnClickListener {
            showEditButton()
            hideTickButton()
            hideCloseButton()
            hideCameraButton()
            clearMap()
            setLicenseImage(data!!.beforeLicenseImage, data!!.afterLicenseImage)
        }
        edt_dob.setOnClickListener {
            mDatePickerDialogWidget.showDatePickerDialog()
        }
        sp_gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                data!!.gender = position
            }

        }
        btn_choose_avatar.setOnClickListener {
            handlePermission(PERMISSION_REQUEST_CODE_AVATAR)
        }
        imv_camera_before.setOnClickListener {
            handlePermission(PERMISSION_REQUEST_CODE_LICENSE_BEFORE)
        }
        imv_camera_after.setOnClickListener {
            handlePermission(PERMISSION_REQUEST_CODE_LICENSE_AFTER)
        }
        btn_save.setOnClickListener {
            if (!onEditChoose) {
                data!!.fullName = edt_name.text.toString()
                data!!.birthday = edt_dob.text.toString()
                data!!.address = edt_address.text.toString()
                data!!.description = edt_des.text.toString()
                if (mapBefore.size > 0) {
                    setFilePathLicenseBefore(mapBefore.keys.first())
                }
                if (mapAfter.size > 0) {
                    setFilePathLicenseAfter(mapAfter.keys.first())
                }
                mPresenter?.updateProfile(data!!)
            } else if (onEditChoose) {
                DialogUtil.showAlertDialogNoTitle(
                        this,
                        "Bạn có xác nhận thay đổi?",
                        false,
                        "Xác nhận",
                        "Hủy bỏ",
                        object : DialogUtil.BaseAlertDialogListener {
                            override fun onPositiveClick(dialogInterface: DialogInterface) {
                                dialogInterface.dismiss()
                                if (mapBefore.size > 0) {
                                    setFilePathLicenseBefore(mapBefore.keys.first())
                                }
                                if (mapAfter.size > 0) {
                                    setFilePathLicenseAfter(mapAfter.keys.first())
                                }
                                mPresenter?.updateProfile(data!!)
                            }

                            override fun onNegativeClick(dialogInterface: DialogInterface) {
                                dialogInterface.dismiss()
                                clearMap()
                                setLicenseImage(data!!.beforeLicenseImage, data!!.afterLicenseImage)
                                hideTickButton()
                                hideCloseButton()
                                showEditButton()
                            }

                        }
                )
            }
        }

    }

    fun setFilePathLicenseBefore(absoluteFilePath: String?) {
        mPresenter?.setFilePath(absoluteFilePath!!, SELECT_IMAGE_FROM_ALBUM_LICENSE_BEFORE)
    }

    fun setFilePathLicenseAfter(absoluteFilePath: String?) {
        mPresenter?.setFilePath(absoluteFilePath!!, SELECT_IMAGE_FROM_ALBUM_LICENSE_AFTER)
    }

    private fun setLicenseImage(mTempLincenseBefore: String?, mTempLicenseAfter: String?) {
        imv_before.setlicenseImage(this, mTempLincenseBefore)
        imv_after.setlicenseImage(this, mTempLicenseAfter)
    }

    private fun initGenderAdapter() {
        mListGender = arrayListOf("Nam", "Nữ")
        mSpinnerGenderAdapter = SpinnerGenderAdapter(this, mListGender!!)
        sp_gender.adapter = mSpinnerGenderAdapter
    }

    private fun getDataIntent() {
        val bundle = intent.extras
        data = bundle.getSerializable(DRIVER_PROFILE) as DriverProfileResponse
        setData(data)
    }

    private fun setData(data: DriverProfileResponse?) {
        imv_avatar.setAvatar(this, data!!.avatar)
        edt_name.text = data.fullName ?: ""
        edt_email.text = data?.email ?: ""
        edt_phone.text = data?.phone ?: ""
        edt_dob.text = data?.birthday ?: ""
        edt_address.text = data?.address ?: ""
        edt_des.setText(
                data.description?.let {
                    it
                } ?: ""
        )
        if (data.gender == 0) {
            sp_gender.setSelection(0)
        } else {
            sp_gender.setSelection(1)
        }
        imv_before.setlicenseImage(this, data.beforeLicenseImage)
        imv_after.setlicenseImage(this, data.afterLicenseImage)
    }

    fun clearMap() {
        mapBefore.clear()
        mapAfter.clear()
    }

    fun showCameraButton() {
        imv_camera_before.visible()
        imv_camera_after.visible()
    }

    fun showEditButton() {
        onEditChoose = false
        imv_edit.visible()
    }

    fun hideEditButton() {
        onEditChoose = true
        imv_edit.gone()
    }

    fun hideCameraButton() {
        imv_camera_before.gone()
        imv_camera_after.gone()
    }

    fun showTickButton() {
        imv_tick_done.visible()
    }

    fun hideTickButton() {
        imv_tick_done.gone()
    }

    fun showCloseButton() {
        imv_close.visible()
    }

    fun hideCloseButton() {
        imv_close.gone()
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, dayOfMonth)
        val dateInCurrent = Calendar.getInstance(TimeZone.getDefault())
        if (calendar.after(dateInCurrent)) {
            onDateError()
            return
        }
        edt_dob.text = "${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}" +
                "/${DateUtil.formatValue(year.toString())}"
    }

    fun handlePermission(requestCodePermission: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requestCodePermission)
        } else {
            selectImageFromAlbum(requestCodePermission)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ToastUtil.show(resources.getString(R.string.request_read_external_storeage))
        } else {
            selectImageFromAlbum(requestCode)
        }
    }

    private fun selectImageFromAlbum(requestCodePermission: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeType = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        when (requestCodePermission) {
            PERMISSION_REQUEST_CODE_AVATAR -> {
                startActivityForResult(intent, SELECT_IMAGE_FROM_ALBUM_AVATAR)
            }
            PERMISSION_REQUEST_CODE_LICENSE_BEFORE -> {
                startActivityForResult(intent, SELECT_IMAGE_FROM_ALBUM_LICENSE_BEFORE)
            }
            PERMISSION_REQUEST_CODE_LICENSE_AFTER -> {
                startActivityForResult(intent, SELECT_IMAGE_FROM_ALBUM_LICENSE_AFTER)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var cursor: Cursor? = null
        val imageUri = data?.data
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        if (imageUri != null) {
            cursor = contentResolver.query(imageUri, filePathColumn, null, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
            val absoluteFilePath = cursor!!.getString(columnIndex)
            setImage(requestCode, imageUri, absoluteFilePath)
        }
    }

    private fun setImage(requestCode: Int, imageUri: Uri?, absoluteFilePath: String?) {
        when (requestCode) {
            SELECT_IMAGE_FROM_ALBUM_AVATAR -> {
                if (imageUri != null) {
                    imv_avatar.setAvatar(this, imv_avatar, imageUri!!)
                    mPresenter?.setFilePath(absoluteFilePath!!, SELECT_IMAGE_FROM_ALBUM_AVATAR)
                }
            }
            SELECT_IMAGE_FROM_ALBUM_LICENSE_BEFORE -> {
                if (imageUri != null) {
                    mapBefore.clear()
                    mapBefore[absoluteFilePath] = imageUri
                    imv_before.setlicenseImage(this, imageUri)
                }
            }
            SELECT_IMAGE_FROM_ALBUM_LICENSE_AFTER -> {
                if (imageUri != null) {
                    mapAfter.clear()
                    mapAfter[absoluteFilePath] = imageUri
                    imv_after.setlicenseImage(this, imageUri)
                }
            }
        }
    }

    override fun onFullNameError() {
        edt_name.setError(resources.getString(R.string.driver_update_profile_name_error))
    }

    override fun onDateError() {
        ToastUtil.show(resources.getString(R.string.driver_update_profile_date_error))
    }

    override fun onAddressError() {
        edt_address.setError(resources.getString(R.string.driver_update_profile_address_error))
    }

    override fun onDescriptionError() {
        edt_des.setError(resources.getString(R.string.driver_update_profile_description_error))
        edt_des.requestFocus()
    }

    override fun onUpdateSuccess() {
        ToastUtil.show(resources.getString(R.string.driver_update_profile_success))
        finish()
    }

}