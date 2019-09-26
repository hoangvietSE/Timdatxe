package com.example.anothertimdatxe.sprinthome.userfindcar

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindCarAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import kotlinx.android.synthetic.main.fragment_user_find_car.*

class UserFindCarFragment : BaseFragment<UserFindCarPresenter>(), UserFindCarView {
    private var mPostCreatedMoreFindCarAdapter: PostCreatedMoreFindCarAdapter? = null

    override val layoutRes: Int
        get() = R.layout.fragment_user_find_car

    companion object {
        fun getInstance(): UserFindCarFragment {
            val fragment = UserFindCarFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getPresenter(): UserFindCarPresenter {
        return UserFindCarPresenterImpl(this)
    }

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mPostCreatedMoreFindCarAdapter = PostCreatedMoreFindCarAdapter(mContext)
        recycler_view_user_find_car.adapter = mPostCreatedMoreFindCarAdapter
        recycler_view_user_find_car.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
    }

    fun showListUserFindCar(list: List<UserPostResponse>) {
        mPostCreatedMoreFindCarAdapter?.addModels(list, false)
    }

    fun clear() {
        mPostCreatedMoreFindCarAdapter!!.clear()
    }

    fun showNoResult(check: Boolean) {
        if (check) {
            no_result_user_find_car.visible()
        } else {
            no_result_user_find_car.gone()
        }
    }
}