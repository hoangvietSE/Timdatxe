package com.example.anothertimdatxe.base.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.customview.CarBookingLoading
import com.example.anothertimdatxe.eventbus.RefreshTokenFailure
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BaseView {

    companion object {
        const val MIN_CLICK_INTERVAL = 1000L
    }

    protected abstract val layoutRes: Int
    protected var mPresenter: T? = null
    protected var toolbar: View? = null
    protected var leftbutton: ImageView? = null
    protected var rightButton: ImageView? = null
    protected var toolbarTitle: TextView? = null
    protected var imvCenter: ImageView? = null
    private var dialog: CarBookingLoading? = null
    private var mLastClickTime: Long = 0L
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
        EventBus.getDefault().register(this);
    }

    abstract fun getPresenter(): T

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.let {
            it.destroy()
        }
        EventBus.getDefault().unregister(this);
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

    protected fun avoidDoubleClick(): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - mLastClickTime < MIN_CLICK_INTERVAL) {
            return true
        }
        mLastClickTime = currentTime
        return false
    }

    fun startActivityAndClearTask(cls: Class<*>) {
        startActivityAndClearTask(this, cls)
    }

    fun startActivityAndClearTaskOrNewTask(cls: Class<*>) {
        startActivityAndClearTaskOrNewTask(this, cls)
    }

    fun startActivityAndClearTop(cls: Class<*>) {
        startActivityAndClearTop(this, cls)
    }

    fun startActivityAndSingleTop(cls: Class<*>) {
        startActivityAndSingleTop(this, cls)
    }

    fun startActivityAndClearTask(context: Context, cls: Class<*>) {
        startActivity(Intent(context, cls).apply {
            //            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun startActivityAndClearTaskOrNewTask(context: Context, cls: Class<*>) {
        startActivity(Intent(context, cls).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun startActivityAndClearTop(context: Context, cls: Class<*>) {
        startActivity(Intent(context, cls).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    fun startActivityAndSingleTop(context: Context, cls: Class<*>) {
        startActivity(Intent(context, cls).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun failRefershToken(event: RefreshTokenFailure) {
        ToastUtil.show("Phiên đăng nhập đã hết. Vui lòng đăng nhập!")
        CarBookingSharePreference.clearAllPreference()
        startActivityAndClearTaskOrNewTask(this, LoginActivity::class.java)
        finish()
    }
}