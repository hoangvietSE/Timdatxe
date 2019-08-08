package com.example.anothertimdatxe.sprinthome.carfinduser

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindUserAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import kotlinx.android.synthetic.main.fragment_car_find_user.*

class CarFindUserFragment : BaseFragment<CarFindUserPresenter>(), CarFindUserView {
    private var mPostCreatedMoreFindUserAdapter: PostCreatedMoreFindUserAdapter? = null
    override val layoutRes: Int
        get() = R.layout.fragment_car_find_user

    companion object {
        fun getInstance(): CarFindUserFragment {
            val fragment = CarFindUserFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getPresenter(): CarFindUserPresenter {
        return CarFindUserPresenterImpl(this)
    }

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mPostCreatedMoreFindUserAdapter = PostCreatedMoreFindUserAdapter(context!!)
        recycler_view_car_find_user.adapter = mPostCreatedMoreFindUserAdapter!!
        recycler_view_car_find_user.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
    }

    fun showListCarFindUser(list: List<DriverPostResponse>) {
        mPostCreatedMoreFindUserAdapter!!.addModels(list, false)
    }

    fun showNoResult(check: Boolean) {
        if (check) {
            no_result_car_find_user.visible()
        } else {
            no_result_car_find_user.gone()
        }
    }
}