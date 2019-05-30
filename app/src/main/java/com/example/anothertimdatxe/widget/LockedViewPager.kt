package com.example.anothertimdatxe.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class LockedViewPager : ViewPager {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        //don't allow swipe screen
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //don't allow swipe screen
        return false
    }
}