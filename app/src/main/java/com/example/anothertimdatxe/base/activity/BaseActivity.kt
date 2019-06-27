package com.example.anothertimdatxe.base.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BaseView
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BaseView {

    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null
    protected var toolbar: View? = null
    protected var leftbutton: ImageView? = null
    protected var toolbarTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        mPresenter = getPresenter()
        initToolBar()
        mPresenter?.let {
            it.start()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    abstract fun getPresenter(): T

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.let {
            it.destroy()
        }
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        leftbutton = findViewById(R.id.btn_left)
        leftbutton?.let {
            it.setOnClickListener {
                onMenuLeftCLick()
            }
        }
        toolbarTitle = findViewById(R.id.toolbar_title)
    }

    open fun onMenuLeftCLick() {
        this.finish()
    }

    fun startActivityAndClearTask(cls: Class<*>) {
        startActivityAndClearTask(this, cls)
    }

    fun startActivityAndClearTask(context: Context, cls: Class<*>) {
        startActivity(Intent(context, cls).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}