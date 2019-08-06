package com.example.anothertimdatxe.widget

import android.text.Editable
import android.text.TextWatcher
import com.example.anothertimdatxe.customview.ClearableEditText
import com.example.anothertimdatxe.util.NumberUtil

class NumberTextWatcher(var editText: ClearableEditText) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)
        try {
            var originalString = s.toString()

            val longval: Long?
            if (originalString.contains(",")) {
                originalString = originalString.replace(",".toRegex(), "")
            }
            longval = java.lang.Long.parseLong(originalString)

//            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
//            formatter.applyPattern("#,###,###,###")
            val formattedString = NumberUtil.formatMoneyInput(longval)

            //setting text after format to EditText
            editText.setText(formattedString)
            editText.setSelection(editText.text.length)
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }
        editText.addTextChangeListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}