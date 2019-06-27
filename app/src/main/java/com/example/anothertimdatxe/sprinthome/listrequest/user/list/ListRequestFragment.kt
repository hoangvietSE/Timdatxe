package com.example.anothertimdatxe.sprinthome.listrequest.user.list

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.fragment.BaseFragment

class ListRequestFragment : BaseFragment<ListRequestPresenter>(), ListRequestView {
    companion object {
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

    }

    override fun getPresenter(): ListRequestPresenter {
        return ListRequestPresenterImpl(this)
    }
}