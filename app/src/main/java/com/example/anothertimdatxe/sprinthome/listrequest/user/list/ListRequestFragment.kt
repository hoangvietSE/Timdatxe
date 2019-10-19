package com.example.anothertimdatxe.sprinthome.listrequest.user.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.SpinnserStatus
import com.example.anothertimdatxe.adapter.UserListBookAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.common.ItemRecyclerViewDecoration
import com.example.anothertimdatxe.entity.response.ListUserBookResponse
import com.example.anothertimdatxe.extension.invisible
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.listrequest.user.detail.UserRequestDetailActivity
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_user_list_post.*

class ListRequestFragment : BaseFragment<ListRequestPresenter>(), ListRequestView, DatePickerDialogWidget.onSetDateSuccessListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener {
    private var mUserListBookAdapter: UserListBookAdapter? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null
    private var mSpinnerStats: SpinnserStatus? = null

    companion object {
        const val REQUEST_CODE_USRE_REQUEST = 9001
        fun getInstance(): ListRequestFragment {
            val bundle = Bundle()
            val listRequestFragment = ListRequestFragment()
            listRequestFragment.arguments = bundle
            return listRequestFragment
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_list_request

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
            refreshData()
        }
        imv_close.setOnClickListener {
            hideIconClose()
            setDate("")
        }
    }

    override fun getPresenter(): ListRequestPresenter {
        return ListRequestPresenterImpl(this)
    }

    private fun initRefreshing() {
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun setAdapter() {
        mUserListBookAdapter = UserListBookAdapter(context!!)
        mUserListBookAdapter?.setLoadingMoreListener(this)
        mUserListBookAdapter?.addOnItemClickListener(this)
        rcv_list_post.adapter = mUserListBookAdapter
        rcv_list_post.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rcv_list_post.addItemDecoration(ItemRecyclerViewDecoration(context!!, R.dimen.margin_8_dp))
    }

    private fun fetchUserListPost() {
        mPresenter?.fetchUserListBook()
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
                .load(R.drawable.bg_list_driver_post_created)
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
        mUserListBookAdapter?.enableLoadingMore(enable)
    }

    override fun showLoadingMore() {
        mUserListBookAdapter?.showLoadingItem(true)
    }

    override fun hideLoadingMore() {
        mUserListBookAdapter?.hideLoadingItem()
    }

    override fun onLoadMore() {
        showLoadingMore()
        fetchUserListPost()
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {
        val data = mUserListBookAdapter?.getItem(position, ListUserBookResponse::class.java)
        activity?.startActivityForResult(Intent(context, UserRequestDetailActivity::class.java).apply {
            putExtra(UserRequestDetailActivity.EXTRA_POST_ID, data?.id)
        }, REQUEST_CODE_USRE_REQUEST)
    }

    override fun showListUserPost(list: List<ListUserBookResponse>) {
        hideLoadingMore()
        if (swipe_refresh.isRefreshing) {
            mUserListBookAdapter?.clear()
        }
        mUserListBookAdapter?.addModels(list, false)
        hideRefreshing()
    }

    fun refreshData(){
        mPresenter?.refreshData()
    }

    override fun showRefreshing() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideRefreshing() {
        swipe_refresh.isRefreshing = false
    }
}