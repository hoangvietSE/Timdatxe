package com.example.anothertimdatxe.sprinthome.city_post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.adapter.CityPostAdapter
import com.example.anothertimdatxe.base.util.GlideApp
import kotlinx.android.synthetic.main.activity_city_post.*
import kotlinx.android.synthetic.main.toolbar.*

class CityPostActivity : AppCompatActivity() {
    private var imageRes: String? = null
    private var city: String? = null
    private var mCityPostAdapter: CityPostAdapter? = null

    companion object {
        const val BANNER_CITY_POST = "banner_city_post"
        const val CITY_POST = "city_post"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_post)
        setToolbar()
        getDataIntent()
        setBanner()
        setViewPagerAdapter()
    }

    private fun setBanner() {
        GlideApp.with(this)
                .load(imageRes)
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(imv_banner_city_post)
    }

    private fun getDataIntent() {
        imageRes = intent.getStringExtra(BANNER_CITY_POST)
        city = intent.getStringExtra(CITY_POST)
    }

    private fun setToolbar() {
        toolbar_title?.let {
            it.text = resources.getString(R.string.city_post_title).toUpperCase()
        }
        btn_left.setOnClickListener {
            finish()
        }
    }

    private fun setViewPagerAdapter() {
        mCityPostAdapter = CityPostAdapter(supportFragmentManager, this, city!!)
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager)
        }
        viewPager.adapter = mCityPostAdapter
    }
}