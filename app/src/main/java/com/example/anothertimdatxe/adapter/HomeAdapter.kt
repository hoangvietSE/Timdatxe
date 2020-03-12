package com.example.anothertimdatxe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class HomeAdapter(fragmentManager: FragmentManager, var mListFragment: ArrayList<Fragment>, var title: ArrayList<String>)
    : WrappingFragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = mListFragment[position]

    override fun getCount() = mListFragment.size

    override fun getPageTitle(position: Int) = title[position]
}