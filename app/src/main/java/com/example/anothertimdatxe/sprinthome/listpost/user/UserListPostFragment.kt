package com.example.anothertimdatxe.sprinthome.listpost.user

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnserStatus
import com.example.anothertimdatxe.adapter.UserListPostAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.common.ItemRecyclerViewDecoration
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.extension.invisible
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_user_list_post.*

class UserListPostFragment : BaseFragment<UserListPostPresenter>(), UserListPostView, DatePickerDialogWidget.onSetDateSuccessListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener {
    private var mUserListPostAdapter: UserListPostAdapter? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mSpinnerStats: SpinnserStatus? = null
    override val layoutRes: Int
        get() = R.layout.fragment_user_list_post

    companion object {
        fun getInstance(): UserListPostFragment {
            val fragment = UserListPostFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getPresenter(): UserListPostPresenter {
        return UserListPostPresenterImpl(this)
    }

    override fun initView() {
        setBanner()
        initDatePicker()
        initStatus()
        setAdapter()
        initRefreshing()
    }

    override fun initListener() {
        tv_date.setOnClickListener {
            mDatePickerDialogWidget?.let {
                it.showDatePickerDialog()
            }
        }
        swipe_refresh.setOnRefreshListener {
            mPresenter?.refreshData()
        }
        imv_close.setOnClickListener {
            hideIconClose()
            setDate("")
        }
    }

    private fun initRefreshing() {
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun setAdapter() {
        mUserListPostAdapter = UserListPostAdapter(context!!)
        mUserListPostAdapter?.setLoadingMoreListener(this)
        mUserListPostAdapter?.addOnItemClickListener(this)
        rcv_list_post.adapter = mUserListPostAdapter
        rcv_list_post.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rcv_list_post.addItemDecoration(ItemRecyclerViewDecoration(context!!, R.dimen.margin_8_dp))
    }

    private fun fetchUserListPost() {
        mPresenter?.fetchUserListPost()
    }

    private fun initStatus() {
        mPresenter?.initSpinnerStatus()
    }

    override fun setSpinnerStatus(listStatus: List<String>) {
        mSpinnerStats = SpinnserStatus(context!!, listStatus)
        sp_status.adapter = mSpinnerStats
        sp_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mPresenter?.setStatus(position)
            }

        }
    }

    private fun initDatePicker() {
        mDatePickerDialogWidget = DatePickerDialogWidget(context!!, this)
    }

    private fun setBanner() {
        GlideApp.with(this)
                .load(R.drawable.banner_driver_posted)
                .into(imv_banner)
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        val date = "${DateUtil.formatValue(dayOfMonth.toString())}/${DateUtil.formatValue(month.toString())}/${DateUtil.formatValue(year.toString())}"
        showIconClose()
        setDate(date)
    }

    private fun setDate(date: String) {
        tv_date.setText(date)
        mPresenter?.setDate(date)
    }

    private fun showIconClose() {
        imv_close.visible()
    }

    private fun hideIconClose() {
        imv_close.invisible()
    }

    override fun enableLoadingMore(enable: Boolean) {
        mUserListPostAdapter?.enableLoadingMore(enable)
    }

    override fun showLoadingMore() {
        mUserListPostAdapter?.showLoadingItem(true)
    }

    override fun hideLoadingMore() {
        mUserListPostAdapter?.hideLoadingItem()
    }

    override fun onLoadMore() {
        fetchUserListPost()
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {

    }

    override fun showListUserPost(list: List<UserListPostEntity>) {
        if (swipe_refresh.isRefreshing) {
            mUserListPostAdapter?.clear()
        }
        mUserListPostAdapter?.addModels(list, false)
        hideRefreshing()
        hideLoadingMore()
    }

    override fun showRefreshing() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideRefreshing() {
        swipe_refresh.isRefreshing = false
    }
}