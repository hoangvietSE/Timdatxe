package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.HotCitiesResponse
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.setImageUrl
import com.example.anothertimdatxe.sprinthome.homefragment.listener.OnItemListner
import kotlinx.android.synthetic.main.item_hot_cities_home.view.*

class HotCitiesHomeAdapter(var context: Context, var mListHotCity: List<HotCitiesResponse>, var mOnItemListner: OnItemListner) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun getCount(): Int {
        return mListHotCity.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = context.inflate(R.layout.item_hot_cities_home, container, false)
        view.imv_home_banner.setImageUrl(context, mListHotCity[position].app_image!!)
        val width = (container.resources.displayMetrics.widthPixels * 0.66666666666667).toInt()
        val params = LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)
        view.imv_home_banner.layoutParams = params
        view.imv_home_banner.setOnClickListener {
            mOnItemListner.onItemClick(position)
        }
        container.addView(view)
        return view
    }

}