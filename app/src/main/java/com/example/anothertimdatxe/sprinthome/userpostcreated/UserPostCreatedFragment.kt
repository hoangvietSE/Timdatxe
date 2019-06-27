package com.example.anothertimdatxe.sprinthome.userpostcreated

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseFragment
import com.example.anothertimdatxe.entity.UserListPostEntity
import com.example.anothertimdatxe.sprinthome.userpostcreated.adapter.UserPostCreatedAdapter
import com.example.anothertimdatxe.sprinthome.userpostcreated.adapter.UserPostCreatedListener
import com.example.anothertimdatxe.widget.BaseRecyclerView
import kotlinx.android.synthetic.main.fragment_post_created.*

class UserPostCreatedFragment : TimdatxeBaseFragment<UserPostCreatedPresenter>(), UserPostCreatedView {

    companion object {
        fun getInstance(): UserPostCreatedFragment {
            var fragment = UserPostCreatedFragment()
            var bundle: Bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_post_created

    override fun initView() {
        getListUserPostCreated()
        initListUserPost()
    }

    override fun getPresenter(): UserPostCreatedPresenter {
        return UserPostCreatedPresenterImpl(this)
    }

    override fun getListUserPostCreated() {
        mPresenter!!.getData()
    }

    override fun initListUserPost() {
        var adapter = UserPostCreatedAdapter(context!!, object : UserPostCreatedListener {
            override fun onCancelPostCreated() {
            }

            override fun onItemClick(position: Int) {
                //show-dialog
            }

        })
        rvUserPostCreated.setAdapter(adapter)
        rvUserPostCreated.setListLayoutManager()
        rvUserPostCreated.setOnRefreshAndLoadMore(object : BaseRecyclerView.onRefreshLoadMoreListener {
            override fun onRefresh() {
                mPresenter!!.getData()
                rvUserPostCreated.hideLoading()
            }

            override fun onLoadMore() {
            }

        })
    }

    override fun showListUserPostCreated(data: List<UserListPostEntity>) {
        rvUserPostCreated.setListItem(data)
    }

    override fun refreshListUserPostCreated() {
    }
}