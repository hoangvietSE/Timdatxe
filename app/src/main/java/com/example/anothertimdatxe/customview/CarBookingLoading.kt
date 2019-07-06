package com.example.anothertimdatxe.customview

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.anothertimdatxe.R

class CarBookingLoading(private val context: Context) {
    private var dialog: AlertDialog? = null

    init {
        var alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        val li = LayoutInflater.from(context)
        val view = li.inflate(R.layout.dialog_loading_base_activity, null, false)
        alertDialogBuilder.setView(view)
        dialog = alertDialogBuilder.create()
        if (dialog!!.window != null) {
            dialog!!.window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)

    }

    fun show() {
        if (dialog != null && !(context as Activity).isFinishing) {
            if (!shown) {
                forceShow()
            }
        }
    }

    private fun forceShow() {
        shown = true
        dialog!!.show()
    }

    fun hide() {
        if (dialog != null && shown) {
            shown = false
            dialog!!.dismiss()
        }
    }


    companion object {
        private var shown: Boolean = false
        private var instance: CarBookingLoading? = null
        fun getInstance(context: Context): CarBookingLoading {
            instance = CarBookingLoading(context)
            return instance!!
        }
    }
}