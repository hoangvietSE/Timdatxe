package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import kotlinx.android.synthetic.main.item_driver_car_image.view.*

class DriverCarPagerAdapter(var context: Context, var mListImage: ArrayList<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = mListImage.size

    override fun getItemPosition(`object`: Any) = POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.item_driver_car_image, container, false)
        view.tv_number.text = "${position + 1}/${mListImage.size}"
        GlideApp.with(context)
                .load(mListImage[position])
                .centerCrop()
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(view.imv_car)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}