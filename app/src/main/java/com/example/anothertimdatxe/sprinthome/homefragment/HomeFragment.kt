package com.example.anothertimdatxe.sprinthome.homefragment

import android.content.Intent
import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.sprinthome.postmore.PostCreatedMoreActivity
import kotlinx.android.synthetic.main.fragment_home.*

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
        btn_find_user.setOnClickListener {
            val intent = Intent(context, PostCreatedMoreActivity::class.java)
            intent.putExtra(PostCreatedMoreActivity.KEY_POST_USER_AND_DRIVER, "find_user")
            startActivity(intent)
        }
        btn_find_car.setOnClickListener {
            val intent = Intent(context, PostCreatedMoreActivity::class.java)
            intent.putExtra(PostCreatedMoreActivity.KEY_POST_USER_AND_DRIVER, "find_car")
            startActivity(intent)
        }
    }

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this)
    }
}