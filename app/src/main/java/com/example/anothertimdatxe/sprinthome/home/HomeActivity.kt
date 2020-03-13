package com.example.anothertimdatxe.sprinthome.home

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
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
import com.example.anothertimdatxe.presentation.map.mapparent.MapParentActivity
import com.example.anothertimdatxe.sprinthome.help.SupportActivity
import com.example.anothertimdatxe.sprinthome.history.HistoryTravelActivity
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemAdapter
import com.example.anothertimdatxe.sprinthome.home.adapter.MenuItemData
import com.example.anothertimdatxe.sprinthome.homefragment.HomeFragment
import com.example.anothertimdatxe.sprinthome.listpost.driver.DriverListPostFragment
import com.example.anothertimdatxe.sprinthome.listpost.user.UserListPostFragment
import com.example.anothertimdatxe.sprinthome.listrequest.driver.DriverListRequestFragment
import com.example.anothertimdatxe.sprinthome.listrequest.user.list.ListRequestFragment
import com.example.anothertimdatxe.sprinthome.profile.driver.profile.DriverProfileFragment
import com.example.anothertimdatxe.sprinthome.profile.driver.updateprofile.DriverUpdateProfileActivity
import com.example.anothertimdatxe.sprinthome.profile.user.UserProfileFragment
import com.example.anothertimdatxe.sprinthome.revenue.RevenueDriverActivity
import com.example.anothertimdatxe.sprinthome.settings.settingsummary.SettingActivity
import com.example.anothertimdatxe.sprinthome.updateprofile.UpdateProfileActivity
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.util.ToastUtil
import com.example.anothertimdatxe.widget.BottomNavigationViewHelper
import com.example.anothertimdatxe.widget.BottomTabLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soict.hoangviet.baseproject.extension.toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_nav_menu.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.Serializable

class HomeActivity : BaseActivity<HomePresenter>(), HomeView, BottomTabLayout.BottomBarListener {

    companion object {
        const val ITEM_MENU_REVENUE = "revenue"
        const val ITEM_MENU_HISTORY = "history"
        const val ITEM_MENU_SUPPORT = "support"
        const val ITEM_MENU_SHARE = "share"
        const val ITEM_MENU_RATING = "rating"
        const val ITEM_MENU_CONNECT_FB = "connectfb"
        //        const val ITEM_MENU_LOG_OUT = "logout"
        const val VP_ITEM_HOME = 0
        const val VP_ITEM_NEWS = 1
        const val VP_ITEM_LIST_REQUEST = 2
        const val VP_ITEM_PROFILES = 3
        const val TAG_ITEM_HOME = "home"
        const val TAG_ITEM_NEWS = "news"
        const val TAG_ITEM_LIST_REQUEST = "list_request"
        const val TAG_ITEM_PROFILES = "profiles"
        const val TIME_BACK_LIMIT = 2000L
        const val REQUEST_CODE_FOR_CREATE_POST = 1998
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
                ITEM_MENU_SHARE -> {
                    goToshareApp()
                }
                ITEM_MENU_RATING -> {
                    goToChPlay()
                }
                ITEM_MENU_CONNECT_FB -> {
                    goToWebBrowser()
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
            toast(resources.getString(R.string.home_back_press))
        }
        backPressTime = System.currentTimeMillis()
    }

    override fun initView() {
        initViewPager()
        setToolbarHome()
        initDrawer()
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
        var driverProfileFragment: Fragment? = null
        var listRequestFragment: Fragment? = null
        var driverListRequestFragment: Fragment? = null
        if (CarBookingSharePreference.getUserData()!!.isUser) {
            listPostCreatedFragment = UserListPostFragment.getInstance()
            listRequestFragment = ListRequestFragment.getInstance()
            userProfileFragment = UserProfileFragment.getInstance()
        } else if (CarBookingSharePreference.getUserData()!!.isDriver) {
            listPostCreatedFragment = DriverListPostFragment.getInstance()
            driverListRequestFragment = DriverListRequestFragment.getInstance()
            driverProfileFragment = DriverProfileFragment.getInstance()
        }
        mListFragment.add(homeFragment)
        if (CarBookingSharePreference.getUserData()!!.isUser) {
            mListFragment.add(listPostCreatedFragment!!)
            mListFragment.add(listRequestFragment!!)
            mListFragment.add(userProfileFragment!!)
        } else if (CarBookingSharePreference.getUserData()!!.isDriver) {
            mListFragment.add(listPostCreatedFragment!!)
            mListFragment.add(driverListRequestFragment!!)
            mListFragment.add(driverProfileFragment!!)
        }
        mFragmentAdapter = BaseFragmentManager(supportFragmentManager, mListFragment)
//        for ((index, fragment) in mListFragment.withIndex()) {
//            when (index) {
//                VP_ITEM_HOME -> {
//                    addFragment(fragment, TAG_ITEM_HOME)
//                }
//                VP_ITEM_NEWS -> {
//                    addFragment(fragment, TAG_ITEM_NEWS)
//                }
//                VP_ITEM_LIST_REQUEST -> {
//                    addFragment(fragment, TAG_ITEM_LIST_REQUEST)
//                }
//                VP_ITEM_PROFILES -> {
//                    addFragment(fragment, TAG_ITEM_PROFILES)
//                }
//            }
//        }
//        bottom_bar.setListener(this)
        BottomNavigationViewHelper.disableShiftMode(bottom_bar)
        bottom_bar.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_home -> {
                        onHomeClick()
                        return true
                    }
                    R.id.item_list_post -> {
                        onNews()
                        return true
                    }
                    R.id.item_post -> {
                        onCreateNews()
                        return true
                    }
                    R.id.item_list_request -> {
                        onRequest()
                        return true
                    }
                    R.id.item_profile -> {
                        onProfile()
                        return true
                    }
                }
                return false
            }

        })
//        onCurrentItem(VP_ITEM_HOME)
        vp_home.adapter = mFragmentAdapter
        vp_home.offscreenPageLimit = 3
        vp_home.currentItem = VP_ITEM_HOME
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                onPage(position)
            }

        })
    }

    private fun onPage(position: Int) {
        when (position) {
            VP_ITEM_HOME -> {
                setToolbarHome()
            }
            VP_ITEM_NEWS -> {
                setToolbarListPost()
            }
            VP_ITEM_LIST_REQUEST -> {
                setToolbarListRequest()
                when {
                    CarBookingSharePreference.getUserData()!!.isUser -> {
                        setToolbarTitle(resources.getString(R.string.driver_list_request_toolbar_title).toUpperCase())
                    }
                    CarBookingSharePreference.getUserData()!!.isDriver -> {
                        setToolbarTitle(resources.getString(R.string.driver_list_request_toolbar_title).toUpperCase())
                    }
                }
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
                            val data = mfragment.getUserProfile()
                            startActivity(Intent(this@HomeActivity, UpdateProfileActivity::class.java).apply {
                                putExtra(UpdateProfileActivity.USER_PROFILE, data)
                            })
                        } else if (mfragment is DriverProfileFragment) {
                            val data = mfragment.getDriverProfile()
                            startActivity(Intent(this@HomeActivity, DriverUpdateProfileActivity::class.java).apply {
                                val bundle = Bundle()
                                bundle.putSerializable(DriverUpdateProfileActivity.DRIVER_PROFILE, data as Serializable)
                                putExtras(bundle)
                            })

                        }
                    }
                    when {
                        CarBookingSharePreference.getUserData()!!.isUser -> {
                            setToolbarTitle(resources.getString(R.string.user_profile_toolbar_title))
                        }
                        CarBookingSharePreference.getUserData()!!.isDriver -> {
                            setToolbarTitle(resources.getString(R.string.driver_profile_toolbar_title))
                        }
                    }
                }
            }
        }
    }

//    private fun addFragment(mFragment: Fragment, tag: String) {
//        supportFragmentManager.beginTransaction().add(R.id.main_container, mFragment).commit()
//    }
//
//    private fun replaceFragment(mFragment: Fragment, tag: String) {
//        supportFragmentManager.beginTransaction().replace(R.id.main_container, mFragment).commit()
//    }

//    private fun onCurrentItem(position: Int) {
//        val transaction = supportFragmentManager.beginTransaction()
//        for ((index, fragment) in mListFragment.withIndex()) {
//            if (index == position) {
//                transaction.show(fragment)
//            } else {
//                transaction.hide(fragment)
//            }
//        }
//        transaction.commit()
//        onPageSelected(position)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_FOR_CREATE_POST -> {
                if (resultCode == Activity.RESULT_OK) {
//                    onCurrentItem(VP_ITEM_NEWS)
                    bottom_bar.selectedItemId = R.id.item_list_post
                    when {
                        CarBookingSharePreference.getUserData()!!.isDriver -> {
                            val fragment = mListFragment[VP_ITEM_NEWS] as DriverListPostFragment
                            fragment.refreshData()
                        }
                        CarBookingSharePreference.getUserData()!!.isUser -> {
                            val fragment = mListFragment[VP_ITEM_NEWS] as UserListPostFragment
                            fragment.refreshData()
                        }
                    }
                }
            }
            ListRequestFragment.REQUEST_CODE_USRE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (mListFragment[2] is ListRequestFragment) {
                        (mListFragment[2] as ListRequestFragment).refreshData()
                    }
                }
            }
            UserListPostFragment.REQUEST_CODE_USER_LIST_POST -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (mListFragment[1] is UserListPostFragment) {
                        (mListFragment[1] as UserListPostFragment).refreshData()
                    }
                }
            }
        }
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

    private fun setToolbarListPost() {
        setToolbarTitle(resources.getString(R.string.driver_list_post_toolbar_title).toUpperCase())
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

    private fun setToolbarListRequest() {
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
        val itemShare = MenuItemData(
                resources.getString(R.string.home_menu_share),
                R.drawable.ic_share,
                R.drawable.ic_share,
                "Chia sẻ"
        )
        val itemRating = MenuItemData(
                resources.getString(R.string.home_menu_rating),
                R.drawable.ic_rating_app,
                R.drawable.ic_rating_app,
                "Đánh giá ứng dụng"
        )
        val itemFacebook = MenuItemData(
                resources.getString(R.string.home_menu_connect_facebook),
                R.drawable.ic_facebook,
                R.drawable.ic_facebook,
                "Kết nối Facebook"
        )
        if (CarBookingSharePreference.getUserData()!!.isDriver) mAdapter.add(itemRevenue)
        mAdapter.add(itemTrip)
//        mAdapter.add(itemMessage)
//        mAdapter.add(itemPromotion)
        mAdapter.add(itemHelp)
        mAdapter.add(itemShare)
        mAdapter.add(itemRating)
        mAdapter.add(itemFacebook)
//        mAdapter.add(itemLogout)
        rv_menu.adapter = mAdapter
        rv_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        updateHeaderMenu(CarBookingSharePreference.getUserData()!!.fullName, CarBookingSharePreference.getUserData()!!.avatar)
        onSettingClick()
    }

    fun updateHeaderMenu(fullName: String, avatar: String?) {
        imv_avatar.setAvatar(this, avatar)
        tv_user_name.text = fullName
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
//        onCurrentItem(VP_ITEM_HOME)
    }

    override fun onNews() {
        vp_home.currentItem = VP_ITEM_NEWS
//        onCurrentItem(VP_ITEM_NEWS)
    }

    override fun onCreateNews() {
        startActivityForResult(Intent(this, MapParentActivity::class.java), REQUEST_CODE_FOR_CREATE_POST)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    override fun onRequest() {
        vp_home.currentItem = VP_ITEM_LIST_REQUEST
//        onCurrentItem(VP_ITEM_LIST_REQUEST)
    }

    override fun onProfile() {
        vp_home.currentItem = VP_ITEM_PROFILES
//        onCurrentItem(VP_ITEM_PROFILES)
    }

    fun logOut() {
        CarBookingSharePreference.clearAllPreference()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun goToHistoryTravelActivity() {
        startActivity(Intent(this@HomeActivity, HistoryTravelActivity::class.java).apply {
            putExtra(HistoryTravelActivity.HISTORY_TRAVEL, CarBookingSharePreference.getUserData()!!.isUser)
        })
    }

    private fun goToRevenueDriver() {
        startActivity(Intent(this@HomeActivity, RevenueDriverActivity::class.java))
    }

    private fun goToSupportActivity() {
        startActivity(Intent(this@HomeActivity, SupportActivity::class.java))
    }

    private fun goToWebBrowser() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("https://facebook.com.vn")
        }
        val intentChooser = Intent.createChooser(intent, "Choose web browser")
        //if no activity that satisfy, return nul
        //if only activity, start immidiately this
        //if multiple satisfy activity, show chooser dialog
        if (intentChooser.resolveActivity(packageManager) != null) {
            startActivity(intentChooser)
        }
    }

    private fun goToChPlay() {
        val packageName = packageName
        try {
            startActivity(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("market://details?id=$packageName")
            })
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            })
        }
    }

    private fun goToshareApp() {
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.setType("text/plain")
        val intentChooser = Intent.createChooser(intentShare, "Chia sẻ ứng ụng Tìm Đặt Xe")
        //Verify the original intent will resolve to at least one activity
        if (intentChooser.resolveActivity(packageManager) != null) {
            startActivity(intentChooser)
        }
    }

}