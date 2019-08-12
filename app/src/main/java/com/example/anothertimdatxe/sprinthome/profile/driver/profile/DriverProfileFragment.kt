package com.example.anothertimdatxe.sprinthome.profile.driver.profile

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.UserReviewDriverAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.DriverProfileResponse
import com.example.anothertimdatxe.entity.response.UserReviewDriverResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.setlicenseImage
import com.example.anothertimdatxe.extension.visible
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_driver_profile.*

class DriverProfileFragment : BaseFragment<DriverProfilePresenter>(), DriverProfileView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    private var mUserReviewDriverAdapter: UserReviewDriverAdapter? = null
    override val layoutRes: Int
        get() = R.layout.fragment_driver_profile

    companion object {
        fun getInstance(): DriverProfileFragment {
            val fragment = DriverProfileFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        mPresenter?.getDriverInfo()
        initAdapter()
        mPresenter?.getUserReviewDriver()
    }

    private fun initAdapter() {
        mUserReviewDriverAdapter = UserReviewDriverAdapter(context!!)
        mUserReviewDriverAdapter!!.setLoadingMoreListener(this)
        recycler_view_profile.adapter = mUserReviewDriverAdapter
    }

    override fun getPresenter(): DriverProfilePresenter {
        return DriverProfilePresenterImpl(this)
    }

    override fun showDriverInfo(data: DriverProfileResponse) {
        imv_avatar.setAvatar(context!!, data.avatar)
        tv_fullname.text = data.fullName
        tv_number_trip.text = data.countBooks.toString()
        mrb.rating = data.vote!!.toFloat()
        rowFullName.setContent(data.fullName)
        rowEmail.setContent(data.email)
        rowPhoneNumber.setContent(data.phone)
        rowDateOfBirth.setContent(data.birthday)
        if (data.gender == 0) {
            rowGender.setContent("Nam")
        } else {
            rowGender.setContent("Nữ")
        }
        rowAddress.setContent(data.address)
        tv_des.text = data.description?.let {
            it.toString()
        } ?: "Mô tả bản thân"
        imv_before.setlicenseImage(context!!, data.beforeLicenseImage)
        imv_after.setlicenseImage(context!!, data.afterLicenseImage)
    }

    override fun onLoadMore() {

    }

    override fun enableLoadMore(enable: Boolean) {
        mUserReviewDriverAdapter!!.enableLoadingMore(enable)
    }

    override fun showLoadMoreProgress() {
        mUserReviewDriverAdapter!!.showLoadingItem(true)
    }

    override fun hideLoadMoreProgress() {
        mUserReviewDriverAdapter!!.hideLoadingItem()
    }

    override fun showListReview(list: List<UserReviewDriverResponse>) {
        mUserReviewDriverAdapter!!.addModels(list, false)
    }

    override fun showNoResult(check: Boolean) {
        if(check){
            tv_no_result.visible()
        }else{
            tv_no_result.gone()
        }
    }
}