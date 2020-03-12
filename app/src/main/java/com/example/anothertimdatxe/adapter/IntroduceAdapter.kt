package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.extension.visible
import kotlinx.android.synthetic.main.item_introduce.view.*

class IntroduceAdapter(var context: Context, var mListImage: ArrayList<String>) : PagerAdapter() {
    companion object {
        const val toturial_url_prefix = "http://api.timdatxe.com/uploads/tutorials/"
    }

    var onItemClick: (() -> Unit)? = null

    override fun isViewFromObject(view: View, `object`: Any) = view == `object` as View

    override fun getCount() = mListImage.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = context.inflate(R.layout.item_introduce, container, false)
        GlideApp.with(context)
                .load(toturial_url_prefix + mListImage[position])
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.bg_white)
                .placeholder(R.drawable.bg_white)
                .into(view.imv_introduce)
        if (position == mListImage.size.minus(1)) {
            view.btn_go_to_home.visible()
        } else {
            view.btn_go_to_home.gone()
        }
        view.btn_go_to_home.setOnClickListener {
            onItemClick?.invoke() ?: throw NullPointerException("Must be initialize item click listener")
        }
        container.addView(view)
        return view
    }

    override fun getItemPosition(`object`: Any) = POSITION_NONE
}