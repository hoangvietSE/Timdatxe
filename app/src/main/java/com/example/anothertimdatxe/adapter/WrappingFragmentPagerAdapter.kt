package com.example.anothertimdatxe.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.anothertimdatxe.widget.WrappingViewPager


/**
 * FragmentPagerAdapter that automatically works with [WrappingViewPager], if you don't want to
 * implement the necessary logic in the adapter yourself.
 *
 *
 * Make sure you only use this adapter with a [WrappingViewPager], otherwise your app
 * will crash!
 *
 * @author Santeri Elo
 * @since 14-06-2016
 */
abstract class WrappingFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mCurrentPosition = -1

    /**
     * @param container View container (instance of [WrappingViewPager]))
     * @param position  Item position
     * @param object    [Fragment]
     */
    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        if (container !is WrappingViewPager) {
            throw UnsupportedOperationException("ViewPager is not a WrappingViewPager")
        }


        val fragment = `object` as Fragment
        if (fragment != null && fragment!!.getView() != null) {
            if (position != mCurrentPosition) {
                mCurrentPosition = position
            }
            container.onPageChanged(fragment!!.getView()!!)
        }
    }
}