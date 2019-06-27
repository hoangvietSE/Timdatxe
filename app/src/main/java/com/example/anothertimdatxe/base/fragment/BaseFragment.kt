package com.example.anothertimdatxe.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BaseView

abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView {
    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null
    protected lateinit var mContext: Context
    protected lateinit var mRootView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = arguments
        mPresenter = getPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(layoutRes, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.destroy()
    }

    abstract override fun initView()

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    abstract fun getPresenter(): T
}