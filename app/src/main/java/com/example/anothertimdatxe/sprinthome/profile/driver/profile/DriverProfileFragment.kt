package com.example.anothertimdatxe.sprinthome.profile.driver.profile

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.UserReviewDriverAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.DriverProfileResponse
import com.example.anothertimdatxe.entity.response.UserReviewDriverResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.setlicenseImage
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.HomeActivity
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_driver_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class DriverProfileFragment : BaseFragment<DriverProfilePresenter>(), DriverProfileView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    private var mUserReviewDriverAdapter: UserReviewDriverAdapter? = null
    private var mResponse: DriverProfileResponse? = null
    private var isLoading = false
    private var enableLoadMore = false
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
        initListenerNestedScrollView()
        initAdapter()
        mPresenter?.getUserReviewDriver()
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    private fun initListenerNestedScrollView() {
        nestedScroolView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    if (isLoading || !enableLoadMore) {
                        return@OnScrollChangeListener
                    }
                    val visibleItemCount = recycler_view_profile.layoutManager!!.itemCount
                    val totalItemCount = recycler_view_profile.layoutManager!!.childCount
                    val pastVisiblesItems = (recycler_view_profile.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (!isLoading) {
                        isLoading = true
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            onLoadMore()
                        }
                    }
                }
            }
        })
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
        mResponse = data
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
        showLoadMoreProgress()
        mUserReviewDriverAdapter!!.setIsLoading(true)
        mPresenter?.getUserReviewDriver()
    }

    override fun enableLoadMore(enable: Boolean) {
        mUserReviewDriverAdapter!!.enableLoadingMore(enable)
        this.enableLoadMore = enable
    }

    override fun showLoadMoreProgress() {
        mUserReviewDriverAdapter!!.showLoadingItem(true)
    }

    override fun hideLoadMoreProgress() {
        mUserReviewDriverAdapter!!.hideLoadingItem()
    }

    override fun showListReview(list: List<UserReviewDriverResponse>) {
        isLoading = false
        hideLoadMoreProgress()
        mUserReviewDriverAdapter!!.addModels(list, false)
    }

    override fun showNoResult(check: Boolean) {
        if (check) {
            tv_no_result.visible()
        } else {
            tv_no_result.gone()
        }
    }

    fun getDriverProfile(): DriverProfileResponse {
        return mResponse!!
    }

    @Subscribe(sticky = true)
    fun updateDriverProfile(data: DriverProfileResponse?) {
        showDriverInfo(data!!)
        (context as HomeActivity).updateHeaderMenu(data!!.fullName!!,data!!.avatar)
        EventBus.getDefault().removeStickyEvent(data)
    }

}