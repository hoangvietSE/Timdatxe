package com.example.anothertimdatxe.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.anothertimdatxe.R
import kotlinx.android.synthetic.main.layout_bottom_bar.view.*

class BottomTabLayout : LinearLayout {
    private lateinit var listener: BottomBarListener
    private lateinit var mTabs: ArrayList<AppCompatTextView>
    private val listIconSelected = intArrayOf(
            R.drawable.ic_home_selected,
            R.drawable.ic_news_selected,
            R.drawable.ic_list_selected,
            R.drawable.ic_profile_selected
    )
    private val listIconNormal = intArrayOf(
            R.drawable.ic_home_unselected,
            R.drawable.ic_news,
            R.drawable.ic_list,
            R.drawable.ic_profile
    )

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout()
        initView()
    }

    fun setLayout() {
        var inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_bottom_bar, this, true)
    }

    fun initView() {
        mTabs = ArrayList()
        mTabs.add(tvTabNotification)
        mTabs.add(tvTabNews)
        mTabs.add(tvTabList)
        mTabs.add(tvTabProfile)
        setSelectedItem(0)
    }

    interface BottomBarListener {
        fun onHomeClick()
        fun onNews()
        fun onCreateNews()
        fun onRequest()
        fun onProfile()
    }

    fun setListener(listener: BottomBarListener) {
        this.listener = listener
        tvTabNotification.setOnClickListener {
            listener.onHomeClick()
            setSelectedItem(0)
        }
        tvTabNews.setOnClickListener {
            listener.onNews()
            setSelectedItem(1)
        }
        tvTabList.setOnClickListener {
            listener.onRequest()
            setSelectedItem(2)
        }
        tvTabProfile.setOnClickListener {
            listener.onProfile()
            setSelectedItem(3)
        }
    }

    fun setSelectedItem(position: Int) {
        for (i in 0 until mTabs.size) {
            if (position == i) {
                mTabs[i].setCompoundDrawablesWithIntrinsicBounds(0, listIconSelected[i], 0, 0)
                mTabs[i].isSelected = true
            } else {
                mTabs[i].setCompoundDrawablesWithIntrinsicBounds(0, listIconNormal[i], 0, 0)
                mTabs[i].isSelected = false
            }
        }
    }
}