package com.example.anothertimdatxe.presentation.book.user

import android.content.Intent
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.entity.response.DriverPostDetailResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.presentation.book.user.confirm.UserConfirmBookingActivity
import com.example.anothertimdatxe.util.Constant
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.util.NumberUtil
import kotlinx.android.synthetic.main.activity_user_book_detail.*
import kotlinx.android.synthetic.main.layout_convinent_trip_user_book.*
import kotlinx.android.synthetic.main.layout_private_trip_user_book.*

class UserBookDetailActivity : BaseActivity<UserBookDetailPresenter>(), UserBookDetailView {
    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
        const val HIGH_WAY = 1
    }

    private var postId: Int = -1
    private var mDriverPostDetailResponse: DriverPostDetailResponse? = null

    override val layoutRes: Int
        get() = R.layout.activity_user_book_detail

    override fun getPresenter(): UserBookDetailPresenter {
        return UserBookDetailPresenterImpl(this)
    }

    override fun initView() {
        getDataIntent()
        setLayoutToolbar()
        setBanner()
        fetchUserBookDetail()
    }

    override fun setListener() {
        btn_book.setOnClickListener {
            startActivity(Intent(this, UserConfirmBookingActivity::class.java).apply {
                putExtra(UserConfirmBookingActivity.EXTRA_DRIVER_POST_ID, postId)
            })
        }
    }

    private fun getDataIntent() {
        postId = intent.getIntExtra(EXTRA_POST_ID, -1)
    }

    private fun setLayoutToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.user_profile_detail).toUpperCase()
        }
    }

    private fun setBanner() {
        GlideApp.with(this)
                .load(R.drawable.banner_driver_posted)
                .into(imv_banner)
    }

    private fun fetchUserBookDetail() {
        mPresenter?.fetchUserBookDetail(postId)
    }

    override fun showUserBookDetail(data: DriverPostDetailResponse) {
        mDriverPostDetailResponse = data
        GlideApp.with(this)
                .load(BuildConfig.BASE_URL + "/${data?.driver?.avatar}")
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(imv_avatar)
        tv_name.text = data?.driver?.fullName
        rating_bar.rating = data?.driver?.vote!!
        when (data?.type) {
            Constant.CONVENIENT_TRIP -> {
                layout_convinent_trip.visible()
                layout_private_trip.gone()
                showPriceConvinent()
            }
            Constant.PRIVATE_TRIP -> {
                layout_convinent_trip.gone()
                layout_private_trip.visible()
                showPricePrivate()
            }
            Constant.BOTH_CONVENIENT_AND_PRIVATE -> {
                layout_convinent_trip.visible()
                layout_private_trip.visible()
                showPriceConvinent()
                showPricePrivate()
            }
        }
        row_starting_point.setRowDetail(data?.startPoint)
        row_ending_point.setRowDetail(data?.endPoint)
        row_car.setRowDetail(data?.car?.fullName)
        row_date.setRowDetail(DateUtil.formatDate(data?.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_24))
        row_distance.setRowDetail(NumberUtil.showDistance(data?.distance?.toString()!!))
        row_number_seat.setRowDetail(data?.emptySeat.toString())
        if (data.high_way == HIGH_WAY) {
            row_way.visible()
            row_way.setRowDetail("Cao tốc")
        } else {
            row_way.gone()
        }
        tv_requirement.text = data?.description ?: ""
    }

    private fun showPriceConvinent() {
        tv_convinent_30_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.priceLevel1.toString())
        tv_convinent_50_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.priceLevel2.toString())
        tv_convinent_70_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.priceLevel3.toString())
        tv_convinent_100_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.regularPrice.toString())
    }

    private fun showPricePrivate() {
        tv_private_50_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.privatePrice1.toString())
        tv_private_100_percent.text = NumberUtil.formatNumber(mDriverPostDetailResponse?.privatePrice2.toString())
    }
}