package com.example.anothertimdatxe.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BaseFragmentManager(fm: FragmentManager, var mListFragment: ArrayList<Fragment>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mListFragment[position]
    }

    override fun getCount(): Int {
        return mListFragment.size
    }
}