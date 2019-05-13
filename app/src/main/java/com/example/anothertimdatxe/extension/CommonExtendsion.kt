package com.example.anothertimdatxe.extension

import android.view.View
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

fun String.isValidStrongPassword():Boolean{
    val pattern = Pattern.compile(STRONG_PASSWORD_PATTERN)
    return pattern.matcher(this).matches()
}