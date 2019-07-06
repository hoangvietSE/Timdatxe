package com.example.anothertimdatxe.extension

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.util.GlideApp
import java.util.regex.Pattern


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.inflate(context: Context, layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, null)
}

fun Context.inflate(context: Context, root: ViewGroup, layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, root, true)
}

fun ImageView.setAvatar(context: Context, imv: ImageView, url: String) {
    GlideApp.with(context)
            .load(BuildConfig.BASE_URL + url)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(imv)
}

fun ImageView.setAvatar(context: Context, imv: ImageView, contentURI: Uri) {
    GlideApp.with(context)
            .load(contentURI)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(imv)
}

const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"
const val EMAIL_TWO_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+.[a-z]+"
const val PHONE_PATTERN = "[0-9]{9,10}"
const val STRONG_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#$%^&*()_=+.,;':\"])(?=\\S+$).{8,}$"

fun String.isValidEmail(): Boolean {
    val parttern = Pattern.compile(EMAIL_PATTERN)
    return parttern.matcher(this).matches()
}

fun String.isValidEmailTwo(): Boolean {
    val pattern = Pattern.compile(EMAIL_TWO_PATTERN)
    return pattern.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    val pattern = Pattern.compile(PHONE_PATTERN)
    return pattern.matcher(this).matches()
}

fun String.isValidStrongPassword(): Boolean {
    val pattern = Pattern.compile(STRONG_PASSWORD_PATTERN)
    return pattern.matcher(this).matches()
}