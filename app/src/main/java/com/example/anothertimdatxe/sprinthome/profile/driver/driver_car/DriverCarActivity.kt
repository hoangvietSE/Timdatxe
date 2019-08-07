package com.example.anothertimdatxe.sprinthome.profile.driver.driver_car

import android.os.Build
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.DriverCarPagerAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.DriverCarDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import kotlinx.android.synthetic.main.activity_driver_car.*

class DriverCarActivity : BaseActivity<DriverCarPresenter>(), DriverCarView {
    companion object {
        const val KEY_VIEW_ONLY = "key_view_only"
        const val DRIVER_CAR_NO_APPROVE = 0
        const val DRIVER_CAR_PENDING_APPROVE = 1
        const val DRIVER_CAR_ACTIVED = 2
        const val DRIVER_CAR_DELETED = 3
    }

    private var mCarId: Int = -1
    private var mKeyView: Boolean = false
    private var mListImage = ArrayList<String>()
    private var mDriverCarPagerAdapter: DriverCarPagerAdapter? = null
    override val layoutRes: Int
        get() = R.layout.activity_driver_car

    override fun getPresenter(): DriverCarPresenter {
        return DriverCarPresenterImpl(this)
    }

    override fun initView() {
        getDataIntent()
        setUpToolbar()
        setUpWithIntent()
        setUpViewPager()
        fetchData()
    }

    private fun fetchData() {
        mPresenter!!.getDriverCarInfo(mCarId)
    }

    private fun setUpWithIntent() {
        if (mKeyView) {
            btn_update.gone()
        } else {
            btn_update.visible()
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
            if (mKeyView) {
                it.text = resources.getString(R.string.driver_car_title_toolbar_driver)
            } else {
                it.text = resources.getString(R.string.driver_car_title_toolbar_mycar)
            }
        }
    }

    private fun getDataIntent() {
        mCarId = intent.getIntExtra("id", -1)
        mKeyView = intent.getBooleanExtra(KEY_VIEW_ONLY, false)
    }

    private fun setUpViewPager() {
        tabIndicator.setupWithViewPager(viewPager)
        mDriverCarPagerAdapter = DriverCarPagerAdapter(this, mListImage)
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = mDriverCarPagerAdapter
    }

    override fun showDriverCarDetail(response: DriverCarDetailResponse) {
        tv_car_name.text = response.carName
        tv_bienso.text = response.licensePlate
        tv_color.text = response.color
        when (response.status) {
            DRIVER_CAR_NO_APPROVE -> {
                tv_status.text = "Không được phê duyệt"
            }
            DRIVER_CAR_PENDING_APPROVE -> {
                tv_status.text = "Đang chờ phê duyệt"
            }
            DRIVER_CAR_ACTIVED -> {
                tv_status.text = "Đang hoạt động"
            }
            DRIVER_CAR_DELETED -> {
                tv_status.text = "Đã xóa"
            }
        }
        tv_handangkiem.text = response.registrationDate
        tv_date_regis.text = response.register_date
        mListImage.add(BuildConfig.BASE_URL + "/" + response.img1)
        mListImage.add(BuildConfig.BASE_URL + "/" + response.img2)
        mListImage.add(BuildConfig.BASE_URL + "/" + response.img3)
        mListImage.add(BuildConfig.BASE_URL + "/" + response.img4)
        mListImage.add(BuildConfig.BASE_URL + "/" + response.img5)
        mDriverCarPagerAdapter!!.notifyDataSetChanged()
    }
}