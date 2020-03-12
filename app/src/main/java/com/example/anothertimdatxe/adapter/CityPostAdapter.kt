package com.example.anothertimdatxe.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.sprinthome.city_post.city_post_fragment.CityPostFragment
import com.example.anothertimdatxe.util.CarBookingSharePreference

class CityPostAdapter(fm: FragmentManager, var context: Context, var city: String) : FragmentStatePagerAdapter(fm) {
    companion object {
        const val PAGE_INDEX_FIRST = 0
        const val PAGE_INDEX_TWO = 1
    }

    private lateinit var tabFragmentCreators: Map<Int, () -> Fragment>

    init {
        when {
            CarBookingSharePreference.getUserData()!!.isDriver ->
                tabFragmentCreators = mapOf(
                        PAGE_INDEX_FIRST to { CityPostFragment.getInstance("user", city) },
                        PAGE_INDEX_TWO to { CityPostFragment.getInstance("driver", city) })

            CarBookingSharePreference.getUserData()!!.isUser ->
                tabFragmentCreators = mapOf(
                        PAGE_INDEX_FIRST to { CityPostFragment.getInstance("driver", city) }
                )
        }
    }

    override fun getItem(position: Int) = tabFragmentCreators[position]?.invoke()
            ?: throw IndexOutOfBoundsException()

    override fun getCount() = tabFragmentCreators.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when {
            CarBookingSharePreference.getUserData()!!.isDriver -> {
                return when (position) {
                    PAGE_INDEX_FIRST -> context.resources.getString(R.string.city_post_tab_one)
                    else -> context.resources.getString(R.string.city_post_tab_two)
                }
            }
            else -> {
                context.resources.getString(R.string.city_post_tab_two)
            }
        }
    }
}