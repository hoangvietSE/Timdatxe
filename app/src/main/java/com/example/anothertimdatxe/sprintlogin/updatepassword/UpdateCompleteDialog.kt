package com.example.anothertimdatxe.sprintlogin.updatepassword

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.inflate
import com.example.anothertimdatxe.sprintlogin.login.LoginActivity

class UpdateCompleteDialog(mContext: Context) : Dialog(mContext, R.style.Theme_AppCompat_Light_Dialog_Alert) {
    init {
        val view = context.inflate(R.layout.dialog_update_complete)
        view.setOnClickListener {
            dismiss()
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)//to set on click
    }
}