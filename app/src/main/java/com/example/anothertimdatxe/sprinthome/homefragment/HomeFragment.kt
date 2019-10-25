package com.example.anothertimdatxe.sprinthome.homefragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.BannerHomeAdapter
import com.example.anothertimdatxe.adapter.HomeAdapter
import com.example.anothertimdatxe.adapter.HotCitiesHomeAdapter
import com.example.anothertimdatxe.adapter.PostCreatedMoreFindUserAdapter
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.entity.response.DriverPostResponse
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.presentation.book.user.UserBookDetailActivity
import com.example.anothertimdatxe.sprinthome.carfinduser.CarFindUserFragment
import com.example.anothertimdatxe.sprinthome.city_post.CityPostActivity
import com.example.anothertimdatxe.sprinthome.homefragment.listener.OnItemListner
import com.example.anothertimdatxe.sprinthome.hotcities.HotCitiesActivity
import com.example.anothertimdatxe.sprinthome.userfindcar.UserFindCarFragment
import com.example.anothertimdatxe.sprintsearch.driver.driversearch.DriverSearchActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import com.example.anothertimdatxe.widget.UltraViewPager
import com.example.anothertimdatxe.widget.transformer.UltraDepthScaleTransformer
import com.example.kotlinapplication.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment<HomeFragmentPresenter>(), HomeFragmentView,
        RecyclerViewAdapter.OnItemClickListener {
    private var mListHotCities: ArrayList<HotCitiesResponse>? = null
    private var mBannerHomeAdapter: BannerHomeAdapter? = null
    private var mTimer: Timer? = null
    private var mCountShowBanner = 0
    private var mList: ArrayList<Fragment> = ArrayList()
    private var mUserFindCarFragment: UserFindCarFragment? = null
    private var mCarFindUserFragment: CarFindUserFragment? = null
    private var mPostCreatedMoreFindUserAdapter: PostCreatedMoreFindUserAdapter? = null
    private var mHomeAdapter: HomeAdapter? = null
    private var mCurrentSelectedScreen = 0
    private var mNextSelectedScreen = 0
    private var mHotCitiesHomeAdapter: HotCitiesHomeAdapter? = null

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
        mPresenter!!.getData(false)
        initRefreshListener()
    }

    private fun initRefreshListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }
        swipeRefresh.setOnRefreshListener {
            if (CarBookingSharePreference?.getUserData()?.isDriver!!) {
                mUserFindCarFragment!!.clear()
                mCarFindUserFragment!!.clear()
            } else {
                mPostCreatedMoreFindUserAdapter?.clear()
            }
            mPresenter?.getData(true)
        }
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
            mList.add(mCarFindUserFragment!!)
            mList.add(mUserFindCarFragment!!)
            mHomeAdapter = HomeAdapter(childFragmentManager, mList, arrayListOf("", ""))
            vp_home_list_post.adapter = mHomeAdapter
            vp_home_list_post.offscreenPageLimit = 2
            vp_home_list_post.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (position == mCurrentSelectedScreen) {
                        // We are moving to next screen on right side
                        if (positionOffset > 0.5) {
                            // Closer to next screen than to current
                            if (position + 1 != mNextSelectedScreen) {
                                mNextSelectedScreen = position + 1;
                                updateState(mNextSelectedScreen);
                            }
                        } else {
                            // Closer to current screen than to next
                            if (position != mNextSelectedScreen) {
                                mNextSelectedScreen = position;
                                updateState(mNextSelectedScreen);
                            }
                        }
                    } else {
                        // We are moving to next screen left side
                        if (positionOffset > 0.5) {
                            // Closer to current screen than to next
                            if (position + 1 != mNextSelectedScreen) {
                                mNextSelectedScreen = position + 1;
                                updateState(mNextSelectedScreen);
                            }
                        } else {
                            // Closer to next screen than to current
                            if (position != mNextSelectedScreen) {
                                mNextSelectedScreen = position;
                                updateState(mNextSelectedScreen);
                            }
                        }
                    }
                }

                override fun onPageSelected(position: Int) {
                    mCurrentSelectedScreen = position
                    mNextSelectedScreen = position
                    setSelectedItem(mCurrentSelectedScreen)
                }

            })

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

    fun updateState(item: Int) {
        when (item) {
            VP_ITEM_CAR_FIND_USER -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_selected, null)
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_nomarl, null)
                } else {
                    btn_car_find_user.background = resources.getDrawable(R.drawable.bg_button_selected)
                    btn_user_find_car.background = resources.getDrawable(R.drawable.bg_button_nomarl)
                }
            }
            VP_ITEM_USER_FIND_CAR -> {
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
        mHotCitiesHomeAdapter = HotCitiesHomeAdapter(context!!, mListHotCities!!, object : OnItemListner {
            override fun onItemClick(position: Int) {
                startActivity(Intent(context!!, CityPostActivity::class.java).apply {
                    putExtra(CityPostActivity.BANNER_CITY_POST, mListHotCities!![position].app_image)
                    putExtra(CityPostActivity.CITY_POST, mListHotCities!![position].name)
                })
            }

        })
        vp_city.adapter = mHotCitiesHomeAdapter
        vp_city.setMultiScreen(0.7f)
        vp_city.setItemRatio(0.8)
        vp_city.setRatio(2.3f)
        vp_city.setAutoMeasureHeight(true)
        vp_city.setInfiniteLoop(true)
        vp_city.setPageTransformer(true, UltraDepthScaleTransformer())
        vp_city.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        vp_city.currentItem = mListHotCities!!.size - 1
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

    override fun showListUserPost(list: List<UserPostResponse>, isRefreshing: Boolean) {
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            mUserFindCarFragment?.showListUserFindCar(list)
            mUserFindCarFragment?.showNoResult(list.size == 0)
        }
    }

    override fun showListDriverPost(list: List<DriverPostResponse>, isRefreshing: Boolean) {
        swipeRefresh.isRefreshing = false
        if (CarBookingSharePreference.getUserData()!!.isDriver) {
            mCarFindUserFragment!!.showListCarFindUser(list)
            mCarFindUserFragment!!.showNoResult(list.size == 0)
        } else {
            mPostCreatedMoreFindUserAdapter = PostCreatedMoreFindUserAdapter(context!!)
            mPostCreatedMoreFindUserAdapter!!.addModels(list, false)
            mPostCreatedMoreFindUserAdapter!!.addOnItemClickListener(this)
            recycler_view_home.adapter = mPostCreatedMoreFindUserAdapter!!
            recycler_view_home.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
            if (list.size == 0) {
                no_result_home.visible()
            } else {
                no_result_home.gone()
            }
        }
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, viewHolder: RecyclerView.ViewHolder?, viewType: Int, position: Int) {
        val data = mPostCreatedMoreFindUserAdapter?.getItem(position, DriverPostResponse::class.java)
        startActivity(Intent(activity, UserBookDetailActivity::class.java).apply {
            putExtra(UserBookDetailActivity.EXTRA_POST_ID, data?.id)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTimer!!.cancel()
        mTimer = null
    }

    override fun showLoadingData() {
        progress_bar_home.visible()
    }

    override fun hideLoadingData() {
        progress_bar_home.gone()
    }
}