package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.*
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.entity.response.DriverCarBrandDetailResponse
import com.example.anothertimdatxe.entity.response.DriverCarBrandResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.widget.GifSizeFilter
import com.example.anothertimdatxe.widget.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.activity_update_driver_car.*
import kotlinx.android.synthetic.main.item_add.view.*


class UpdateDriverCarActivity : BaseActivity<UpdateDriverCarPresenter>(), UpdateDriverCarView {
    private var mImageAdapter: ImageAdapter? = null
    private var mSpinnerCarBrandAdapter: SpinnerCarBrandAdapter? = null
    private var mSpinnerCarNameAdapter: SpinnerCarNameAdapter? = null
    private var mSpinnerDoiXe: SpinnerSeatAdapter? = null
    private var isFull: Boolean = false
    private var mListDoiXe: MutableList<String> = mutableListOf()
    private var mCarBrandId: Int? = null
    private var mCarVersion: String? = null
    private var mCarID: Int? = null
    private var isSpinnerCarName: Boolean = true
    override val layoutRes: Int
        get() = com.example.anothertimdatxe.R.layout.activity_update_driver_car

    companion object {
        const val REQUEST_PERMISSION_ALBUM = 1000
        const val SELECT_IMAGE_FROM_ALBUM = 1998
    }

    override fun getPresenter(): UpdateDriverCarPresenter {
        return UpdateDriverCarPresenterImpl(this)
    }

    override fun initView() {
        setUpToolbar()
        addButtonNoImage()
        addButtonNoImage()
        addButtonNoImage()
        addButtonNoImage()
        mPresenter?.setImage(mutableListOf())
        mPresenter?.fetchDriverCarBrand()
        mPresenter?.initCarName()
        initDoiXeAdapter()
    }

    override fun setListener() {
        cb_car_name.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isSpinnerCarName = false
                formCarName.gone()
                edt_car_name.visible()
            } else {
                isSpinnerCarName = true
                formCarName.visible()
                edt_car_name.gone()
            }
        }
        edt_date_regis.setOnClickListener {
            var mDatePickerDialogWidget = DatePickerDialogWidget(this, object : DatePickerDialogWidget.onSetDateSuccessListener {
                override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
                    edt_date_regis.setText("${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}" +
                            "/${DateUtil.formatValue(year.toString())}")
                }
            })
            mDatePickerDialogWidget.showDatePickerDialog()
        }
        edt_handangkiem.setOnClickListener {
            var mDatePickerDialogWidget = DatePickerDialogWidget(this, object : DatePickerDialogWidget.onSetDateSuccessListener {
                override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
                    edt_handangkiem.setText("${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}" +
                            "/${DateUtil.formatValue(year.toString())}")
                }
            })
            mDatePickerDialogWidget.showDatePickerDialog()
        }
        btn_add_car.setOnClickListener {
            mPresenter?.createDriverCar(
                    mCarBrandId!!,
                    isSpinnerCarName, mCarID!!,
                    edt_car_name.text.toString(),
                    mCarVersion!!,
                    edt_number_seat.text.toString(),
                    edt_color.text.toString(),
                    edt_license_plate.text.toString(),
                    edt_handangkiem.text.toString(),
                    edt_date_regis.text.toString())
        }
    }

    private fun initDoiXeAdapter() {
        mListDoiXe.add("Chọn đời xe")
        for (i in 2019 downTo 1990 step 1) {
            mListDoiXe.add("${i}")
        }
        mSpinnerDoiXe = SpinnerSeatAdapter(this, mListDoiXe)
        sp_doixe.adapter = mSpinnerDoiXe
        sp_doixe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mCarVersion = mListDoiXe[position]
            }

        }
    }

    private fun setUpToolbar() {
        toolbar?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.setBackgroundColor(resources.getColor(com.example.anothertimdatxe.R.color.colorPrimary, null))
            } else {
                it.setBackgroundColor(resources.getColor(com.example.anothertimdatxe.R.color.colorPrimary))
            }
        }
        leftbutton?.setOnClickListener {
            finish()
        }
        toolbarTitle?.let {
            it.text = resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_title).toUpperCase()

        }
    }

    override fun setAdapter(list: MutableList<DriverCarImage>) {
        mImageAdapter = ImageAdapter(this, list, object : OnClickListener {
            override fun onItemAddClick() {
                handlePermission()
            }

            override fun onCancelClick(pos: Int) {
                mPresenter?.deleteImage(pos)
            }
        })
        rcv_car.adapter = mImageAdapter

    }

    private fun handlePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_ALBUM)
        } else {
            selectImageFromAlbum()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_ALBUM -> {
                if (grantResults != null && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    ToastUtil.show(resources.getString(com.example.anothertimdatxe.R.string.request_read_external_storeage))
                } else {
                    selectImageFromAlbum()
                }
            }
        }
    }

    private fun selectImageFromAlbum() {
//        val intent = Intent()
//        intent.setType("image/*")
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.setAction(Intent.ACTION_GET_CONTENT)
//        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE_FROM_ALBUM)
        Matisse.from(this@UpdateDriverCarActivity)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(5)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(com.example.anothertimdatxe.R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(Glide4Engine())
                .theme(R.style.Matisse_Zhihu)
                .forResult(SELECT_IMAGE_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SELECT_IMAGE_FROM_ALBUM -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val mSelected = Matisse.obtainResult(data)
                    for (i in 0..mSelected.size - 1) {
                        val cursor = contentResolver.query(mSelected.get(i), filePathColumn, null, null, null, null)
                        cursor.moveToFirst()
                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                        val absoluteFilePath = cursor.getString(columnIndex)
                        val mDriverCarImage = DriverCarImage(absoluteFilePath, mSelected.get(i), ImageAdapter.VIEW_TYPE_IMAGE, true)
                        if (!isFull) {
                            mPresenter?.addImage(mDriverCarImage)
                        }
                    }
//                    if (data.data != null) {//select one image
//                        val uriImage = data.data
//                        val cursor = contentResolver.query(uriImage, filePathColumn, null, null, null, null)
//                        cursor.moveToFirst()
//                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                        val absoluteFilePath = cursor.getString(columnIndex)
//                        val mDriverCarImage = DriverCarImage(absoluteFilePath, uriImage, ImageAdapter.VIEW_TYPE_IMAGE, true)
//                        mPresenter?.addImage(mDriverCarImage)
//                    } else {
//                        if (data.clipData != null) {//select multiple image
//                            val mClipData = data.clipData
//                            for (i in 0..mClipData.itemCount - 1) {
//                                val uriImage = mClipData.getItemAt(i).uri
//                                val cursor = contentResolver.query(uriImage, filePathColumn, null, null, null, null)
//                                cursor.moveToFirst()
//                                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                                val absoluteFilePath = cursor.getString(columnIndex)
//                                val mDriverCarImage = DriverCarImage(absoluteFilePath, uriImage, ImageAdapter.VIEW_TYPE_IMAGE, true)
//                                if (!isFull) {
//                                    mPresenter?.addImage(mDriverCarImage)
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }

    fun addButtonNoImage() {
        val view = this.inflate(R.layout.item_add)
        val btnAdd: ImageView = view.iv_add
        val params: LinearLayout.LayoutParams = btnAdd.layoutParams as LinearLayout.LayoutParams
        params.marginEnd = resources.getDimensionPixelSize(com.example.anothertimdatxe.R.dimen.margin_8_dp)
        btnAdd.layoutParams = params
        btnAdd.setImageResource(R.drawable.bg_add_button_not_active)
        if (btnAdd.parent != null) {
            (btnAdd.parent as ViewGroup).removeView(btnAdd)
        }
        view_not_image.addView(btnAdd)
    }

    override fun setButtonAdd() {
        mImageAdapter?.addImageIndex(0, DriverCarImage("", null, ImageAdapter.VIEW_TYPE_BTN_ADD, false))
    }

    override fun addImageIndex(pos: Int, image: DriverCarImage) {
        mImageAdapter?.addImageIndex(pos, image)
        removeButtonNotSelected()

    }

    override fun addLastImage(pos: Int, image: DriverCarImage) {
        isFull = mImageAdapter?.removeImageAt(pos)!!
        mImageAdapter?.addImageIndex(pos, image)
        removeButtonNotSelected()
    }

    private fun removeButtonNotSelected() {
        if (view_not_image.childCount > 0) {
            view_not_image.removeViewAt(view_not_image.childCount - 1)
        }
    }

    private fun addButtonNotSelected() {
        if (view_not_image.childCount < 4) {
            addButtonNoImage()
        }
    }

    override fun removeItemAndAddButton(pos: Int) {
        isFull = false
        mImageAdapter?.removeImageAt(pos)!!
        mImageAdapter?.addImageIndex(pos, DriverCarImage("", null, ImageAdapter.VIEW_TYPE_BTN_ADD, false))

    }

    override fun removeItem(pos: Int) {
        mImageAdapter?.removeImageAt(pos)!!
        if (isFull) {
            isFull = false
            mImageAdapter?.addImageIndex(mImageAdapter?.getListSize()!!, DriverCarImage("", null, ImageAdapter.VIEW_TYPE_BTN_ADD, false))
        } else {
            addButtonNotSelected()
        }
    }

    override fun showListDriverCarBrand(list: MutableList<DriverCarBrandResponse>) {
        mSpinnerCarBrandAdapter = SpinnerCarBrandAdapter(this, list)
        sp_brand_car.adapter = mSpinnerCarBrandAdapter
        sp_brand_car.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    mSpinnerCarNameAdapter?.clear()
                    mSpinnerCarNameAdapter?.addItem(DriverCarBrandDetailResponse(-1, "Chọn tên xe", -1))
                } else {
                    mPresenter?.fetchDriverCarName(list[position].id)
                }
                mCarBrandId = list[position].id
            }

        }
    }

    override fun showListDriverCarName(list: MutableList<DriverCarBrandDetailResponse>) {
        mSpinnerCarNameAdapter = SpinnerCarNameAdapter(this, list)
        sp_car_name.adapter = mSpinnerCarNameAdapter
        sp_car_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mCarID = list[position].id
            }

        }
    }

    override fun onCarBrandError() {
        ToastUtil.show(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_car_brand))
    }

    override fun onCarNameSpinnerError() {
        ToastUtil.show(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_car_name_spinner))
    }

    override fun onCarNameEdittextError() {
        edt_car_name.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_car_name_edittext))
        edt_car_name.requestFocus()
    }

    override fun onDoixeError() {
        ToastUtil.show(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_doixe))
    }

    override fun onNumberSeatError() {
        edt_number_seat.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_number_seat))
        edt_number_seat.requestFocus()
    }

    override fun onLicensePlateError() {
        edt_license_plate.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_license_plate))
        edt_license_plate.requestFocus()
    }

    override fun onColorError() {
        edt_color.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_color))
        edt_color.requestFocus()
    }

    override fun onRegistrationNotLessThanRegiterDate() {
        edt_handangkiem.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_registration_not_less_than_regiter_date))
        edt_handangkiem.requestFocus()
    }

    override fun onDateRegisEmptyError() {
        edt_date_regis.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_date_regis_empty))
        edt_date_regis.requestFocus()
    }

    override fun onDateRegisInFutureError() {
        edt_date_regis.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_date_regis_future))
        edt_date_regis.requestFocus()
    }

    override fun onDateRegistrationEmptyError() {
        edt_handangkiem.setError(resources.getString(com.example.anothertimdatxe.R.string.update_driver_car_error_registration_not_less_than_regiter_date))
        edt_handangkiem.requestFocus()
    }
}