package com.example.anothertimdatxe.widget

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.example.anothertimdatxe.R
import java.util.*

class DatePickerDialogWidget(var context: Context, var mListener: OnSetDateSuccessListener) : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mListener.onSetDateSuccess(year, month + 1, dayOfMonth)
    }

    val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    fun showDatePickerDialog() {
        var dialog = DatePickerDialog(
                context,
                R.style.DialogTheme,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    interface OnSetDateSuccessListener {
        fun onSetDateSuccess(year: Int, month: Int, dayOfMonth: Int)
    }
}