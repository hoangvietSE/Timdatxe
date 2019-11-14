package com.example.anothertimdatxe.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.anothertimdatxe.util.NumberUtil

class AmountTextWatcher(val editText: EditText) : TextWatcher {
    private var beforeText = ""
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeText = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val initPosition = start + before
        val numberOfDigitRightCursor = getNumberOfDigits(beforeText.substring(initPosition, beforeText.length))
        val newAmount = NumberUtil.formatValue(s.toString())
        editText.removeTextChangedListener(this)
        editText.setText(newAmount)
        editText.setSelection(getPositionOfCursor(numberOfDigitRightCursor, newAmount))
        editText.addTextChangedListener(this)
    }

    private fun getNumberOfDigits(text: String): Int {
        var count = 0
        for (i in text) {
            if (i.isDigit()) {
                count++
            }
        }
        return count
    }

    private fun getPositionOfCursor(numberOfDigitRightCursor: Int, text: String): Int {
        var position = 0
        var count = numberOfDigitRightCursor
        for (i in text.reversed()) {
            if (count == 0) {
                break
            }
            if (i.isDigit()) {
                count--
            }
            position++
        }
        return text.length - position
    }
}