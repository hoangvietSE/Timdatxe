package com.example.anothertimdatxe.sprinthome

import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.base.adapter.BaseFragmentManager
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemAdapter
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemData
import com.example.anothertimdatxe.sprinthome.homefragment.HomeFragment
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.widget.BottomTabLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_nav_menu.*

class HomeActivity : BaseActivity<HomePresenter>(), HomeView, BottomTabLayout.BottomBarListener {

    companion object {
        var ITEM_LOG_OUT = 5
        var VP_HOME = 0
        var VP_NEWS = 1
        var VP_LISTS = 2
        var VP_PROFILES = 3
    }

    private var mToggle: ActionBarDrawerToggle? = null
    private lateinit var mAdapter: MenuItemAdapter
    private var mListFragment: ArrayList<Fragment> = arrayListOf()
    private var mFragmentAdapter: BaseFragmentManager? = null
    private var mListener: BaseRvListener = object : BaseRvListener {
        override fun onItemClick(position: Int) {
            when (position) {
                ITEM_LOG_OUT -> logOut()
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_home

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this)
    }

    override fun onMenuLeftCLick() {
        drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun initView() {
        setUpToolBar()
        initDrawer()
        initViewPager()
    }

    fun setUpToolBar() {
        toolBar?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.background = resources.getDrawable(R.color.colorPrimary, null)
            } else {
                it.background = resources.getDrawable(R.color.colorPrimary)
            }
        }
        leftbutton?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.setImageResource(R.drawable.ic_menu)
            } else {
                it.setImageResource(R.drawable.ic_menu)
            }
        }
    }

    fun initViewPager() {
        var homeFragment = HomeFragment.newInstance()
        mListFragment.add(homeFragment)
        mFragmentAdapter = BaseFragmentManager(supportFragmentManager, mListFragment)
        bottom_bar.setListener(this)
        vp_home.adapter = mFragmentAdapter
        vp_home.offscreenPageLimit = 3
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    //do-something
                }
            }

        })
    }

    fun initDrawer() {
        mToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(mToggle!!)
        mAdapter = MenuItemAdapter(this, mListener)

        val itemPayment = MenuItemData(
                R.drawable.ic_payment_selected,
                R.drawable.ic_payment_normal,
                "Thông tin tài khoản"
        )
        val itemTrip = MenuItemData(
                R.drawable.ic_trip_selected,
                R.drawable.ic_trip_normal,
                "Các chuyến đi"
        )
        val itemMessage = MenuItemData(
                R.drawable.ic_message_selected,
                R.drawable.ic_message_normal,
                "Tin nhắn"
        )
        val itemPromotion = MenuItemData(
                R.drawable.ic_promotion_selected,
                R.drawable.ic_promotion_normal,
                "Ưu dãi"
        )
        val itemHelp = MenuItemData(
                R.drawable.ic_help_selected,
                R.drawable.ic_help_normal,
                "Trợ giúp"
        )
        val itemLogout = MenuItemData(
                R.drawable.ic_logout_selected,
                R.drawable.ic_logout_normal,
                "Đăng xuất"
        )

        mAdapter.add(itemPayment)
        mAdapter.add(itemTrip)
        mAdapter.add(itemMessage)
        mAdapter.add(itemPromotion)
        mAdapter.add(itemHelp)
        mAdapter.add(itemLogout)
        rv_menu.adapter = mAdapter
        rv_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onHomeClick() {
        vp_home.currentItem = VP_HOME
    }

    override fun onNews() {
        vp_home.currentItem = VP_NEWS
    }

    override fun onCreateNews() {
        //new_activity
    }

    override fun onRequest() {
        vp_home.currentItem = VP_LISTS
    }

    override fun onProfile() {
        vp_home.currentItem = VP_PROFILES
    }

    fun logOut() {
        CarBookingSharePreference.clearAllPreference()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

}