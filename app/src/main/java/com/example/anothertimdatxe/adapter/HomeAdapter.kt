package com.example.anothertimdatxe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class HomeAdapter(fragmentManager: FragmentManager, var mListFragment: ArrayList<Fragment>, var title: ArrayList<String>)
    : WrappingFragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return mListFragment[position]
    }

    override fun getCount(): Int {
        return mListFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}