package com.example.anothertimdatxe.widget

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import com.example.anothertimdatxe.R
import java.util.*

class TimePickerDialogWidget(var context: Context, var mListener: onTimeSetListener) : TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mListener.onSetTimeSuccess(view, hourOfDay, minute)
    }

    val calendar = Calendar.getInstance(TimeZone.getDefault())
    fun showTimePickerDialog() {
        val dialog = TimePickerDialog(
                context,
                R.style.DialogTheme,
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        )
        dialog.show()
    }

    interface onTimeSetListener {
        fun onSetTimeSuccess(view: TimePicker?, hourOfDay: Int, minute: Int)
    }
}