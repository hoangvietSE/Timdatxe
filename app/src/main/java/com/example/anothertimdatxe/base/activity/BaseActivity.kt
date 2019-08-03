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
import com.example.anothertimdatxe.customview.CarBookingLoading

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BaseView {

    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null
    protected var toolbar: View? = null
    protected var leftbutton: ImageView? = null
    protected var rightButton: ImageView? = null
    protected var toolbarTitle: TextView? = null
    protected var imvCenter: ImageView? = null
    private var dialog: CarBookingLoading? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        mPresenter = getPresenter()
        dialog = CarBookingLoading.getInstance(this)
        initToolBar()
        mPresenter?.let {
            it.start()
        }
        setListener()
    }

    abstract fun getPresenter(): T

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.let {
            it.destroy()
        }
    }

    override fun showLoading() {
        dialog!!.show()
    }

    override fun hideLoading() {
        dialog!!.hide()
    }

    fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        leftbutton = findViewById(R.id.btn_left)
        rightButton = findViewById(R.id.btn_right)
        imvCenter = findViewById(R.id.imv_center)
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

    protected open fun setListener() {
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