package com.soict.hoangviet.baseproject.extension

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp

/**
 * Load Image with GlideApp
 */
inline fun <reified T : Any> ImageView.loadImage(
        context: Context,
        image: T?,
        @DrawableRes placeHolder: Int = R.drawable.img_default,
        @DrawableRes error: Int= R.drawable.img_default
) {
    when (T::class) {
        Int::class.java, String::class.java, Uri::class.java -> {
            GlideApp.with(context)
                .load(image)
                .placeholder(placeHolder)
                .error(error)
                .into(this)
        }
    }

}
