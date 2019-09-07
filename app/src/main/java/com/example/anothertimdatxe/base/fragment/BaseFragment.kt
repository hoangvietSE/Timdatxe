package com.example.anothertimdatxe.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import kotlinx.android.synthetic.main.shimmer_layout.*

abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView {
    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null
    protected lateinit var mContext: Context
    protected lateinit var mRootView: View
    private var mLastTimeClick: Long = 0

    companion object {
        const val MIN_CLICK_INTERVAL = 1000L
    }

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
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.destroy()
    }

    abstract override fun initView()
    protected open fun initListener() {
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    abstract fun getPresenter(): T

    protected fun showPreviewLoading() {
        shimmer_layout.visible()
        shimmer_layout.startShimmer()
    }

    protected fun hidePreviewLoading() {
        shimmer_layout.stopShimmer()
        shimmer_layout.gone()
    }

    protected fun avoidDoubleClick(): Boolean {
        val timeInClick = SystemClock.elapsedRealtime()
        if (timeInClick - mLastTimeClick < MIN_CLICK_INTERVAL) {
            return true
        }
        mLastTimeClick = timeInClick
        return false
    }
}