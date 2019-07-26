package com.example.anothertimdatxe.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment.CityPostFragment
import com.example.anothertimdatxe.util.CarBookingSharePreference

class CityPostAdapter(fm: FragmentManager, var context: Context, var city: String) : FragmentStatePagerAdapter(fm) {
    private var mListFragment: ArrayList<CityPostFragment>? = null

    init {
        if(CarBookingSharePreference.getUserData()!!.isDriver){
            mListFragment = arrayListOf(
                    CityPostFragment.getInstance("user", city),
                    CityPostFragment.getInstance("driver", city)
            )
        }else if(CarBookingSharePreference.getUserData()!!.isUser){
            mListFragment = arrayListOf(
                    CityPostFragment.getInstance("driver", city)
            )
        }
    }

    override fun getItem(position: Int): Fragment {
        return mListFragment!![position]
    }

    override fun getCount(): Int {
        return mListFragment?.size!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(CarBookingSharePreference.getUserData()!!.isDriver){
            if (position == 0) {
                return context.resources.getString(R.string.city_post_tab_one)
            } else {
                return context.resources.getString(R.string.city_post_tab_two)
            }
        }
        return context.resources.getString(R.string.city_post_tab_two)
    }
}