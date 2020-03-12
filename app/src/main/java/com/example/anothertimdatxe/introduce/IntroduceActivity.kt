package com.example.anothertimdatxe.introduce

import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.IntroduceAdapter
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.sprinthome.home.HomeActivity
import com.example.anothertimdatxe.util.CarBookingSharePreference
import kotlinx.android.synthetic.main.activity_introduce.*

class IntroduceActivity : BaseActivity<IntroducePresenter>(), IntroduceView {
    private var mListImage: ArrayList<String>? = null
    private var mIntroduceAdapter: IntroduceAdapter? = null
    override val layoutRes: Int
        get() = R.layout.activity_introduce

    override fun getPresenter(): IntroducePresenter {
        return IntroducePresenterImpl(this)
    }

    override fun initView() {
        if (CarBookingSharePreference.getUserData()?.isDriver!!) {
            mListImage = initListImageDriver()
        } else {
            mListImage = initListImageUser()
        }
        initAdapter()
    }

    private fun initAdapter() {
        mIntroduceAdapter = IntroduceAdapter(this, mListImage!!)
        mIntroduceAdapter?.onItemClick = {
            if (CarBookingSharePreference.getUserData()?.isDriver!!) {
                CarBookingSharePreference.setWelcomeDriverApp()
            } else {
                CarBookingSharePreference.setWelcomeUserApp()
            }
            startActivityAndClearTask(HomeActivity::class.java)
            clearCache()
            finish()
        }
        view_pager_introduce.adapter = mIntroduceAdapter
        indicator.setupWithViewPager(view_pager_introduce)
        view_pager_introduce.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == mListImage?.size?.minus(1)) {
                    indicator.gone()
                } else {
                    indicator.visible()
                }
            }

        })
    }

    private fun clearCache() {
        Thread(Runnable {
            Glide.get(this).clearDiskCache()
        })
        Glide.get(this).clearMemory()
    }

    private fun initListImageUser(): ArrayList<String> {
        return arrayListOf("tutorials_user1.png", "tutorials_user2.png", "tutorials_user3.png", "tutorials_user4.png", "tutorials_user5.png")
    }

    private fun initListImageDriver(): ArrayList<String> {
        return arrayListOf("tutorials_driver1.png", "tutorials_driver2.png", "tutorials_driver3.png", "tutorials_driver4.png", "tutorials_driver5.png", "tutorials_driver6.png")
    }
}