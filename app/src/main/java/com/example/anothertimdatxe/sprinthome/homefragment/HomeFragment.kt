package com.example.anothertimdatxe.sprinthome.homefragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.BannerHomeAdapter
import com.example.anothertimdatxe.adapter.HomeAdapter
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindUserAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.carfinduser.CarFindUserFragment
import com.example.anothertimdatxe.sprinthome.hotcities.HotCitiesActivity
import com.example.anothertimdatxe.sprinthome.userfindcar.UserFindCarFragment
import com.example.anothertimdatxe.sprintsearch.driver.driversearch.DriverSearchActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment<HomeFragmentPresenter>(), HomeFragmentView {
    private var mListHotCities: ArrayList<HotCitiesResponse>? = null
    private var mBannerHomeAdapter: BannerHomeAdapter? = null
    private var mTimer: Timer? = null
    private var mCountShowBanner = 0
    private var mList: ArrayList<Fragment>? = ArrayList()
    private var mUserFindCarFragment: UserFindCarFragment? = null
    private var mCarFindUserFragment: CarFindUserFragment? = null
    private var mPostCreatedMoreFindUserAdapter: PostCreatedMoreFindUserAdapter? = null
    private var mHomeAdapter: HomeAdapter? = null

    companion object {
        fun newInstance(): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        const val DELAY_MS = 1000L
        const val PERIOD_MS = 5000L
        const val VP_ITEM_CAR_FIND_USER = 0
        const val VP_ITEM_USER_FIND_CAR = 1
    }

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {
        setUpHomePosted()
        mPresenter!!.getData()
    }

    override fun initListener() {
        tv_search.setOnClickListener {
            if (CarBookingSharePreference.getUserData()!!.isDriver) {
                startActivity(Intent(context, DriverSearchActivity::class.java))
            }
        }
        tv_hot_cities.setOnClickListener {
            mListHotCities?.let {
                val intent = Intent(activity, HotCitiesActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelableArrayList(HotCitiesActivity.HOT_CITIES, it)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        btn_car_find_user.setOnClickListener {
            setSelectedItem(VP_ITEM_CAR_FIND_USER)
        }
        btn_user_find_car.setOnClickListener {
            setSelectedItem(VP_ITEM_USER_FIND_CAR)
        }
    }

    private fun setUpHomePosted() {
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            vp_home_list_post.visible()
            csl.visible()
            rl_home_list.gone()
            mCarFindUserFragment = CarFindUserFragment.getInstance()
            mUserFindCarFragment = UserFindCarFragment.getInstance()
            mList!!.add(mCarFindUserFragment!!)
            mList!!.add(mUserFindCarFragment!!)
            mHomeAdapter = HomeAdapter((context!! as AppCompatActivity).supportFragmentManager, mList!!, arrayListOf("",""))
            vp_home_list_post.adapter = mHomeAdapter!!
            setSelectedItem(VP_ITEM_USER_FIND_CAR)

        } else {
            vp_home_list_post.gone()
            rl_home_list.visible()
            csl.gone()
        }
    }

    fun setSelectedItem(item: Int) {
        when (item) {
            VP_ITEM_CAR_FIND_USER -> {
                vp_home_list_post.currentItem = VP_ITEM_CAR_FIND_USER
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_selected, null)
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_nomarl, null)
                } else {
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_selected)
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_nomarl)
                }
            }
            VP_ITEM_USER_FIND_CAR -> {
                vp_home_list_post.currentItem = VP_ITEM_USER_FIND_CAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_selected, null)
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_nomarl, null)
                } else {
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_selected)
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_nomarl)
                }
            }
        }
    }

    override fun getPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenterImpl(this)
    }

    override fun showListHotCities(data: ArrayList<HotCitiesResponse>) {
        mListHotCities = data
    }

    override fun showListBanners(list: List<BannerHomeResponse>) {
        mBannerHomeAdapter = BannerHomeAdapter(context!!, list)
        vp_banner.adapter = mBannerHomeAdapter
        if (list.isNotEmpty()) {
            indicator.setViewPager(vp_banner)
        }
        mTimer = Timer()
        val handler = Handler()
        val runnable = Runnable {
            vp_banner.setCurrentItem(mCountShowBanner, true)
            mCountShowBanner++
            if (mCountShowBanner == list.size) {
                mCountShowBanner = 0
            }
        }
        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }

        }, DELAY_MS, PERIOD_MS)
        vp_banner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mCountShowBanner = position
            }

        })
    }

    override fun showListUserPost(list: List<UserPostResponse>) {
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            mUserFindCarFragment!!.showListUserFindCar(list)
        }
    }

    override fun showListDriverPost(list: List<DriverPostResponse>) {
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            mCarFindUserFragment!!.showListCarFindUser(list)
        } else {
            mPostCreatedMoreFindUserAdapter = PostCreatedMoreFindUserAdapter(context!!)
            mPostCreatedMoreFindUserAdapter!!.addModels(list, false)
            recycler_view_home.adapter = mPostCreatedMoreFindUserAdapter!!
            recycler_view_home.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTimer!!.cancel()
        mTimer = null
    }
}