package com.example.anothertimdatxe.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import com.example.anothertimdatxe.R

object DialogUtil {
    fun showConfirmDiaglog(context: Context, layout: Int, setCancel: Boolean, resBackground: Int): Dialog {
        var dialog: Dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        dialog.setCancelable(setCancel)
        dialog.window.setBackgroundDrawableResource(resBackground)
        dialog.show()
        return dialog
    }

    private fun showMessageDialog(context: Context, msg: Int, title: Int, okListener: DialogInterface.OnClickListener?): AlertDialog {
        var builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage(msg)
        builder.setPositiveButton("OK", okListener)
        var alertDialog = builder.create()
        return alertDialog
    }

    fun showConfirmDiaglogNotCancel(context: Context, layout: Int, resBackground: Int): Dialog {
        return showConfirmDiaglog(context, layout, false, resBackground)
    }

    fun showMessageDialog(context: Context, msg: Int): AlertDialog {
        return showMessageDialog(context, msg, R.string.alert_message_title, null)
    }
}