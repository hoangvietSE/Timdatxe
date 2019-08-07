package com.example.anothertimdatxe.sprinthome.homefragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.viewpager.widget.ViewPager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.BannerHomeAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.sprinthome.hotcities.HotCitiesActivity
import com.example.anothertimdatxe.sprinthome.postmore.PostCreatedMoreActivity
import com.example.anothertimdatxe.sprintsearch.driver.driversearch.DriverSearchActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : BaseFragment<HomeFragmentPresenter>(), HomeFragmentView {
    private var mListHotCities: ArrayList<HotCitiesResponse>? = null
    private var mBannerHomeAdapter: BannerHomeAdapter? = null
    private var mTimer: Timer? = null
    private var mCountShowBanner = 0

    companion object {
        fun newInstance(): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        const val DELAY_MS = 1000L
        const val PERIOD_MS = 5000L
    }

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {
        mPresenter!!.getData()
        btn_find_user.setOnClickListener {
            val intent = Intent(context, PostCreatedMoreActivity::class.java)
            intent.putExtra(PostCreatedMoreActivity.KEY_POST_USER_AND_DRIVER, "find_user")
            startActivity(intent)
        }
        btn_find_car.setOnClickListener {
            val intent = Intent(context, PostCreatedMoreActivity::class.java)
            intent.putExtra(PostCreatedMoreActivity.KEY_POST_USER_AND_DRIVER, "find_car")
            startActivity(intent)
        }
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
        if(list.isNotEmpty()){
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
}