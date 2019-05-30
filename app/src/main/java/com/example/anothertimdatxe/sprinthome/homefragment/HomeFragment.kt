package com.example.anothertimdatxe.sprinthome.homefragment

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.mvp.BaseView

class HomeFragment : BaseFragment<HomePresenter>(), HomeView {

    companion object {
        fun newInstance(): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {

    }

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this)
    }
}