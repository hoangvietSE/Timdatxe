package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.entity.response.BannerHomeResponse
import com.example.anothertimdatxe.extension.setImageUrl
import kotlinx.android.synthetic.main.item_home_banner.view.*

class BannerHomeAdapter(var context: Context, var mListBanner: List<BannerHomeResponse>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any) = view == `object` as View

    override fun getCount() = mListBanner.size


    override fun getItemPosition(`object`: Any) = POSITION_NONE


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, container, false)
        val width = (container.resources.displayMetrics.widthPixels * 0.333333).toInt()
        val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width)
        view.imv_home_banner.layoutParams = param
        view.imv_home_banner.setImageUrl(context, mListBanner[position].image!!)
        container.addView(view)
        return view
    }
}