package com.example.anothertimdatxe.sprinthome.postmore

import android.os.Build
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindCarAdapter
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindUserAdapter
import com.example.anothertimdatxe.adapter.SpinnerSeatAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.request.PostCreatedFindCarRequest
import com.example.anothertimdatxe.request.PostCreatedFindUserRequest
import com.example.anothertimdatxe.util.DateUtil
import com.example.anothertimdatxe.widget.DatePickerDialogWidget
import com.example.anothertimdatxe.adapter.EndlessLoadingRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_post_created_more.*

class PostCreatedMoreActivity : BaseActivity<PostCreatedMorePresenter>(), PostCreatedMoreView,
        DatePickerDialogWidget.OnSetDateSuccessListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {

    companion object {
        const val KEY_POST_USER_AND_DRIVER = "key_post_user_and_driver"
    }

    private var mKey: String? = null
    private var mFindUserAdapter: PostCreatedMoreFindUserAdapter? = null
    private var mFindCarAdapter: PostCreatedMoreFindCarAdapter? = null
    private var mDatePickerDialog: DatePickerDialogWidget? = null
    private var listSeat: ArrayList<String> = arrayListOf("Chọn số ghế", "Từ 1 đến 10 ghế", "Từ 10 đến 20 ghế", "Lớn hơn 20 ghế")
    private var mSpinnerSeatAdapter: SpinnerSeatAdapter? = null
    private var mSeatSelected: String? = null
    private var listData: MutableList<Any>? = mutableListOf()

    override val layoutRes: Int
        get() = R.layout.activity_post_created_more

    override fun getPresenter(): PostCreatedMorePresenter {
        return PostCreatedMorePresenterImpl(this)
    }

    override fun initView() {
        getKey()
        setToolbar()
        tv_date.setOnClickListener {
            initDatePickerDialog()
        }
        initSeat()
        getData(false)
        initAdapter()
        setRefresh()
        btn_search.setOnClickListener {
            when (mKey) {
                "find_user" -> {
                    val request = PostCreatedFindUserRequest()
                    request.empty_seat = mSeatSelected
                    request.start_point = edt_starting_point.text.toString()
                    request.end_point = edt_ending_point.text.toString()
                    if (tv_date.text.isNullOrEmpty()) {
                        request.start_time = ""
                    } else {
                        request.start_time = DateUtil.formatDate(tv_date.text.toString(), DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
                    }
                    mPresenter!!.getListSearchFindUser(request)
                }
                "find_car" -> {
                    val request = PostCreatedFindCarRequest()
                    request.number_seat = mSeatSelected
                    request.start_point = edt_starting_point.text.toString()
                    request.end_point = edt_ending_point.text.toString()
                    if (tv_date.text.isNullOrEmpty()) {
                        request.start_time = ""
                    } else {
                        request.start_time = DateUtil.formatDate(tv_date.text.toString(), DateUtil.DATE_FORMAT_23, DateUtil.DATE_FORMAT_1)
                    }
                    mPresenter!!.getListSearchFindCar(request)
                }
            }
        }
    }

    private fun setRefresh() {
        setColorRefreshing()
        swipeRefresh.setOnRefreshListener {
            showRefreshing()
            getData(true)
            hideRefreshing()
        }
    }

    private fun setColorRefreshing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }
    }

    private fun showRefreshing() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideRefreshing() {
        swipeRefresh.isRefreshing = false
    }

    private fun initAdapter() {
        when (mKey) {
            "find_user" -> {
                mFindUserAdapter = PostCreatedMoreFindUserAdapter(this)
                mFindUserAdapter!!.setLoadingMoreListener(this)
                recyclerView.adapter = mFindUserAdapter
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }
            "find_car" -> {
                mFindCarAdapter = PostCreatedMoreFindCarAdapter(this)
                mFindCarAdapter!!.setLoadingMoreListener(this)
                recyclerView.adapter = mFindCarAdapter
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun getData(isRefreshing: Boolean) {
        when (mKey) {
            "find_user" -> {
                getListPostFindUser(isRefreshing)
            }
            "find_car" -> {
                getListPostFindCar(isRefreshing)
            }
        }
    }

    private fun getListPostFindUser(isRefreshing: Boolean) {
        mPresenter!!.getListFindUser(isRefreshing)
    }

    private fun getListPostFindCar(isRefreshing: Boolean) {
        mPresenter!!.getListFindCar(isRefreshing)
    }

    private fun setToolbar() {
        toolbarTitle?.let {
            when (mKey) {
                "find_user" -> {
                    it.text = resources.getString(R.string.find_user).toUpperCase()
                }
                "find_car" -> {
                    it.text = resources.getString(R.string.find_car).toUpperCase()
                }
            }
        }
    }

    private fun initSeat() {
        mSpinnerSeatAdapter = SpinnerSeatAdapter(this, listSeat)
        spinner.adapter = mSpinnerSeatAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSeatSelected = mSpinnerSeatAdapter?.getItem(position).toString()
            }

        }
    }

    private fun initDatePickerDialog() {
        mDatePickerDialog?.showDatePickerDialog() ?: run {
            mDatePickerDialog = DatePickerDialogWidget(this, this)
            mDatePickerDialog?.showDatePickerDialog()
        }
    }

    private fun getKey() {
        mKey = intent.getStringExtra(KEY_POST_USER_AND_DRIVER)
    }

    override fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int) {
        val date: String = "${setValue(dayOfMonth.toString())}/${setValue((month + 1).toString())}/${setValue(year.toString())}"
        tv_date.text = date
        imv_close.visible()
        imv_close.setOnClickListener {
            tv_date.text = ""
            imv_close.gone()
        }
    }

    fun setValue(value: String): String {
        if (value.length == 1) {
            return "0$value"
        } else {
            return value
        }
    }

    override fun onLoadMore() {
        showLoadingMoreProgress()
        getData(false)
    }

    override fun showListPostFindUser(data: List<DriverPostResponse>, isRefreshing: Boolean) {
        listData!!.add(data)
        if (isRefreshing) {
            mFindUserAdapter!!.clear()
        }
        hideLoadingMoreProgress()
        mFindUserAdapter!!.addModels(data, false)
    }

    override fun showListPostFindCar(data: List<UserPostResponse>, isRefreshing: Boolean) {
        listData!!.add(data)
        if (isRefreshing) {
            mFindCarAdapter!!.clear()
        }
        hideLoadingMoreProgress()
        mFindCarAdapter!!.addModels(data, false)
    }

    override fun showListSearchFindUser(data: List<DriverPostResponse>) {
        listData!!.clear()
        listData!!.add(data)
        mFindUserAdapter!!.clear()
        mFindUserAdapter!!.addModels(data, false)
    }

    override fun showListSearchFindCar(data: List<UserPostResponse>) {
        listData!!.clear()
        listData!!.add(data)
        mFindCarAdapter!!.clear()
        mFindCarAdapter!!.addModels(data, false)
    }

    override fun showLoadingProgress() {
        item_loading.visible()
    }

    override fun hideLoadingProgress() {
        item_loading.gone()
    }



    override fun showLoadingMoreProgress() {
        when (mKey) {
            "find_user" -> {
                mFindUserAdapter!!.showLoadingItem(true)
            }
            "find_car" -> {
                mFindCarAdapter!!.showLoadingItem(true)
            }
        }
    }

    override fun hideLoadingMoreProgress() {
        when (mKey) {
            "find_user" -> {
                mFindUserAdapter!!.hideLoadingItem()
            }
            "find_car" -> {
                mFindCarAdapter!!.hideLoadingItem()
            }
        }
    }

    override fun enableLoadingMore(boolean: Boolean) {
        when (mKey) {
            "find_user" -> {
                mFindUserAdapter!!.enableLoadingMore(boolean)
            }
            "find_car" -> {
                mFindCarAdapter!!.enableLoadingMore(boolean)
            }
        }
    }
}