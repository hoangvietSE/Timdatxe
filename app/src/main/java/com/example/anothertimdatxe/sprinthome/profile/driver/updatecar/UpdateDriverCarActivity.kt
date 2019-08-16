package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import android.app.Activity
import android.content.Intent
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
import com.example.anothertimdatxe.util.ToastUtil
import kotlinx.android.synthetic.main.activity_update_driver_car.*
import kotlinx.android.synthetic.main.item_add.view.*

class UpdateDriverCarActivity : BaseActivity<UpdateDriverCarPresenter>(), UpdateDriverCarView {
    private var mImageAdapter: ImageAdapter? = null
    private var mSpinnerCarBrandAdapter: SpinnerCarBrandAdapter? = null
    private var mSpinnerCarNameAdapter: SpinnerCarNameAdapter? = null
    private var mSpinnerDoiXe: SpinnerSeatAdapter? = null
    private var titleCarName: DriverCarBrandDetailResponse? = null
    private var isFull: Boolean = false
    private var mListCarBrand: MutableList<DriverCarBrandResponse> = mutableListOf()
    private var mListCarName: MutableList<DriverCarBrandDetailResponse> = mutableListOf()
    private var mListDoiXe: MutableList<String> = mutableListOf()
    override val layoutRes: Int
        get() = R.layout.activity_update_driver_car

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
        initCarNameAdapter()
        initDoiXeAdapter()
    }

    override fun setListener() {
        cb_car_name.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formCarName.gone()
                edt_car_name.visible()
            } else {
                formCarName.visible()
                edt_car_name.gone()
            }
        }
        edt_date_regis.setOnClickListener {

        }
        edt_handangkiem.setOnClickListener {

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

            }

        }
    }

    private fun setUpToolbar() {
        toolbar?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.setBackgroundColor(resources.getColor(R.color.colorPrimary, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
        }
        leftbutton?.setOnClickListener {
            finish()
        }
        toolbarTitle?.let {
            it.text = resources.getString(R.string.update_driver_car_title).toUpperCase()

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
                    ToastUtil.show(resources.getString(R.string.request_read_external_storeage))
                } else {
                    selectImageFromAlbum()
                }
            }
        }
    }

    private fun selectImageFromAlbum() {
        val intent = Intent()
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SELECT_IMAGE_FROM_ALBUM -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (data.data != null) {//select one image
                        val uriImage = data.data
                        val cursor = contentResolver.query(uriImage, filePathColumn, null, null, null, null)
                        cursor.moveToFirst()
                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                        val absoluteFilePath = cursor.getString(columnIndex)
                        val mDriverCarImage = DriverCarImage(absoluteFilePath, uriImage, ImageAdapter.VIEW_TYPE_IMAGE, true)
                        mPresenter?.addImage(mDriverCarImage)
                    } else {
                        if (data.clipData != null) {//select multiple image
                            val mClipData = data.clipData
                            for (i in 0..mClipData.itemCount - 1) {
                                val uriImage = mClipData.getItemAt(i).uri
                                val cursor = contentResolver.query(uriImage, filePathColumn, null, null, null, null)
                                cursor.moveToFirst()
                                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                                val absoluteFilePath = cursor.getString(columnIndex)
                                val mDriverCarImage = DriverCarImage(absoluteFilePath, uriImage, ImageAdapter.VIEW_TYPE_IMAGE, true)
                                if (!isFull) {
                                    mPresenter?.addImage(mDriverCarImage)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun addButtonNoImage() {
        val view = this.inflate(R.layout.item_add)
        val btnAdd: ImageView = view.iv_add
        val params: LinearLayout.LayoutParams = btnAdd.layoutParams as LinearLayout.LayoutParams
        params.marginEnd = resources.getDimension(R.dimen.margin_10_dp).toInt()
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

    override fun addLastImage(image: DriverCarImage) {
        isFull = mImageAdapter?.removeImageAt(4)!!
        mImageAdapter?.addImageIndex(4, image)
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
        mImageAdapter?.removeImageAt(pos)!!
        mImageAdapter?.addImageIndex(pos, DriverCarImage("", null, ImageAdapter.VIEW_TYPE_BTN_ADD, false))

    }

    override fun removeItem(pos: Int) {
        mImageAdapter?.removeImageAt(pos)!!
        addButtonNotSelected()
    }

    override fun showListDriverCarBrand(list: List<DriverCarBrandResponse>) {
        val title = DriverCarBrandResponse()
        title.brand = "Chọn hãng xe"
        mListCarBrand.add(title)
        mListCarBrand.addAll(list)
        mSpinnerCarBrandAdapter = SpinnerCarBrandAdapter(this, mListCarBrand)
        sp_brand_car.adapter = mSpinnerCarBrandAdapter
        sp_brand_car.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    mListCarName.clear()
                    mListCarName.add(titleCarName!!)
                    mSpinnerCarNameAdapter?.notifyDataSetChanged()
                } else {
                    mPresenter?.fetchDriverCarName(mListCarBrand[position].id)
                }
            }

        }
    }

    private fun initCarNameAdapter() {
        titleCarName = DriverCarBrandDetailResponse()
        titleCarName?.name = "Chọn tên xe"
        mListCarName.add(titleCarName!!)
        mSpinnerCarNameAdapter = SpinnerCarNameAdapter(this, mListCarName)
        sp_car_name.adapter = mSpinnerCarNameAdapter
        sp_car_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
    }

    override fun showListDriverCarName(list: List<DriverCarBrandDetailResponse>) {
        mListCarName.addAll(list)
        mSpinnerCarNameAdapter?.notifyDataSetChanged()
    }
}