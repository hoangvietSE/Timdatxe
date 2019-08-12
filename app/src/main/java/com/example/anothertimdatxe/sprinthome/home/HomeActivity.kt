package com.example.anothertimdatxe.sprinthome

import android.content.Intent
import android.os.Build
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
import com.example.anothertimdatxe.eventbus.GetProfileSuccess
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.help.SupportActivity
import com.example.anothertimdatxe.sprinthome.history.HistoryTravelActivity
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemAdapter
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemData
import com.example.anothertimdatxe.sprinthome.homefragment.HomeFragment
import com.example.anothertimdatxe.sprinthome.listrequest.user.list.ListRequestFragment
import com.example.anothertimdatxe.sprinthome.profile.user.UserProfileFragment
import com.example.anothertimdatxe.sprinthome.revenue.RevenueDriverActivity
import com.example.anothertimdatxe.sprinthome.settings.SettingActivity
import com.example.anothertimdatxe.sprinthome.updateprofile.UpdateProfileActivity
import com.example.anothertimdatxe.sprinthome.userpostcreated.UserPostCreatedFragment
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.BottomTabLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_nav_menu.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeActivity : BaseActivity<HomePresenter>(), HomeView, BottomTabLayout.BottomBarListener {

    companion object {
        const val ITEM_MENU_REVENUE = "revenue"
        const val ITEM_MENU_HISTORY = "history"
        const val ITEM_MENU_SUPPORT = "support"
        //        const val ITEM_MENU_LOG_OUT = "logout"
        const val VP_ITEM_HOME = 0
        const val VP_ITEM_NEWS = 1
        const val VP_ITEM_LISTS = 2
        const val VP_ITEM_PROFILES = 3
        const val TIME_BACK_LIMIT = 2000L
    }

    private var mToggle: ActionBarDrawerToggle? = null
    private lateinit var mAdapter: MenuItemAdapter
    private var mListFragment: ArrayList<Fragment> = arrayListOf()
    private var mFragmentAdapter: BaseFragmentManager? = null
    private var isLoadingProfileSuccess: Boolean = false
    private var backPressTime: Long = 0L
    private var mListener: BaseRvListener = object : BaseRvListener {
        override fun onItemClick(position: Int) {
            when (mAdapter.getListItem().get(position).name) {
                ITEM_MENU_REVENUE -> {
                    goToRevenueDriver()
                }

                ITEM_MENU_HISTORY -> {
                    goToHistoryTravelActivity()
                }

                ITEM_MENU_SUPPORT -> {
                    goToSupportActivity()
                }

//                ITEM_MENU_LOG_OUT -> {
//                    logOut()
//                }
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

    override fun onSettingClick() {
        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        if (backPressTime + TIME_BACK_LIMIT > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            ToastUtil.show(resources.getString(R.string.home_back_press))
        }
        backPressTime = System.currentTimeMillis()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun initView() {
        setToolbarHome()
        initDrawer()
        initViewPager()
    }

    private fun setToolbarTitle(string: String) {
        toolbarTitle?.let {
            it.text = string.toUpperCase()
        }
    }

    fun initViewPager() {
        var homeFragment = HomeFragment.newInstance()
        var listPostCreatedFragment: Fragment? = null
        var userProfileFragment: Fragment? = null
        var listRequestFragment: Fragment? = null
        if (CarBookingSharePreference.getUserData()!!.isUser) {
            listPostCreatedFragment = UserPostCreatedFragment.getInstance()
            listRequestFragment = ListRequestFragment.getInstance()
            userProfileFragment = UserProfileFragment.getInstance()
        }
        mListFragment.add(homeFragment)
        if (CarBookingSharePreference.getUserData()!!.isUser) {
            mListFragment.add(listPostCreatedFragment!!)
            mListFragment.add(listRequestFragment!!)
            mListFragment.add(userProfileFragment!!)
        }
        mFragmentAdapter = BaseFragmentManager(supportFragmentManager, mListFragment)
        bottom_bar.setListener(this)
        vp_home.adapter = mFragmentAdapter
        vp_home.offscreenPageLimit = 3
        vp_home.currentItem = VP_ITEM_HOME
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    VP_ITEM_HOME -> {
                        setToolbarHome()
                    }
                    VP_ITEM_PROFILES -> {
                        setToolbarProfile()
                        rightButton?.let {
                            if (isLoadingProfileSuccess) {
                                it.visible()
                            } else {
                                it.gone()
                            }
                            it.visible()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                it.setImageDrawable(resources.getDrawable(R.drawable.ic_edit, null))
                            } else {
                                it.setImageDrawable(resources.getDrawable(R.drawable.ic_edit))
                            }
                            it.setOnClickListener {
                                var mfragment: Fragment = mListFragment[position]
                                if (mfragment is UserProfileFragment) {
                                    var data = mfragment.getUserProfile()
                                    startActivity(Intent(this@HomeActivity, UpdateProfileActivity::class.java).apply {
                                        putExtra(UpdateProfileActivity.USER_PROFILE, data)
                                    })
                                }
                            }
                            when {
                                CarBookingSharePreference.getUserData()!!.isUser -> {
                                    setToolbarTitle(resources.getString(R.string.user_profile_toolbar_title))
                                }
                                CarBookingSharePreference.getUserData()!!.isDriver -> {

                                }
                            }
                        }
                    }
                }
            }

        })
    }

    private fun setToolbarHome() {
        toolbarTitle?.gone()
        imvCenter?.visible()
        toolbar?.let {
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
        imvCenter?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.background = resources.getDrawable(R.drawable.app_title, null)
            } else {
                it.background = resources.getDrawable(R.drawable.app_title)
            }
        }
        rightButton?.let {
            it.visible()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.setImageDrawable(resources.getDrawable((R.drawable.ic_notification), null))
            } else {
                it.setImageDrawable(resources.getDrawable((R.drawable.ic_notification)))
            }
        }
    }

    private fun setToolbarProfile() {
        toolbarTitle?.visible()
        imvCenter?.gone()
        toolbar?.let {
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

    fun initDrawer() {
        mToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(mToggle!!)
        mAdapter = MenuItemAdapter(this, mListener)

        val itemRevenue = MenuItemData(
                resources.getString(R.string.home_menu_revenue),
                R.drawable.ic_payment_selected,
                R.drawable.ic_payment_normal,
                "Thống kê doanh thu"
        )
        val itemTrip = MenuItemData(
                resources.getString(R.string.home_menu_history),
                R.drawable.ic_trip_selected,
                R.drawable.ic_trip_normal,
                "Lịch sử chuyến đi"
        )
//        val itemMessage = MenuItemData(
//                R.drawable.ic_message_selected,
//                R.drawable.ic_message_normal,
//                "Tin nhắn"
//        )
//        val itemPromotion = MenuItemData(
//                R.drawable.ic_promotion_selected,
//                R.drawable.ic_promotion_normal,
//                "Ưu dãi"
//        )
        val itemHelp = MenuItemData(
                resources.getString(R.string.home_menu_support),
                R.drawable.ic_help_selected,
                R.drawable.ic_help_normal,
                "Trợ giúp"
        )
//        val itemLogout = MenuItemData(
//                R.drawable.ic_logout_selected,
//                R.drawable.ic_logout_normal,
//                "Đăng xuất"
//        )

        if (CarBookingSharePreference.getUserData()!!.isDriver) mAdapter.add(itemRevenue)
        mAdapter.add(itemTrip)
//        mAdapter.add(itemMessage)
//        mAdapter.add(itemPromotion)
        mAdapter.add(itemHelp)
//        mAdapter.add(itemLogout)
        rv_menu.adapter = mAdapter
        rv_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        updateHeaderMenu(CarBookingSharePreference.getUserData()!!.full_name, CarBookingSharePreference.getUserData()!!.avatar)
        onSettingClick()
    }

    fun updateHeaderMenu(full_name: String, avatar: String?) {
        imv_avatar.let {
            it.setAvatar(this, avatar)
        }
        tv_user_name.text = full_name
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showEditButton(getProfileSuccess: GetProfileSuccess) {
        when (getProfileSuccess.checkSuccess) {
            true -> {
                isLoadingProfileSuccess = true
                rightButton!!.visible()
            }
            false -> {
                isLoadingProfileSuccess = false
                rightButton!!.gone()
            }
        }
    }

    override fun onHomeClick() {
        vp_home.currentItem = VP_ITEM_HOME
    }

    override fun onNews() {
        vp_home.currentItem = VP_ITEM_NEWS
    }

    override fun onCreateNews() {
        //new_activity
    }

    override fun onRequest() {
        vp_home.currentItem = VP_ITEM_LISTS
    }

    override fun onProfile() {
        vp_home.currentItem = VP_ITEM_PROFILES
    }

    fun logOut() {
        CarBookingSharePreference.clearAllPreference()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun goToHistoryTravelActivity() {
        startActivity(Intent(this@HomeActivity, HistoryTravelActivity::class.java).apply {
            putExtra(HistoryTravelActivity.HISTORY_TRAVEL, if (CarBookingSharePreference.getUserData()!!.isUser) true else false)
        })
    }

    private fun goToRevenueDriver() {
        startActivity(Intent(this@HomeActivity, RevenueDriverActivity::class.java))
    }

    private fun goToSupportActivity() {
        startActivity(Intent(this@HomeActivity, SupportActivity::class.java))
    }

}