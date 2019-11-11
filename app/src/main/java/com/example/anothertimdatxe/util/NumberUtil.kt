package com.example.anothertimdatxe.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.regex.Pattern

object NumberUtil {
    const val STRING_PARTTERN = "(.)*(\\d)(.)*"
    private var formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
    private var formatterMoney = DecimalFormat("#,###,###,###", DecimalFormatSymbols(Locale.US))

    fun formatNumber(value: String): String {
        return formatter.format(value.toInt()) + " VNĐ"
    }

    fun formatMoneyInput(longVal: Long): String {
        return formatterMoney.format(longVal)
    }

    fun showDistance(value: String): String {
        return value + " km"
    }

    fun showPercentage(percent: Double): String {
        return "${(percent*100).toInt()}%"
    }

    fun isNumberString(input: String): Boolean {
        return Pattern.matches(STRING_PARTTERN, input)
    }

    fun replaceNumber(value: String, oldChar: String, newChar: String): String {
        if (value.contains(oldChar)) {
            return value.replace(oldChar, newChar)
        }
        return value
    }

    fun formatValue(value: String): String {
        try {
            return formatter.format(removeSpecialCharacters(value).toInt())
        } catch (e: NumberFormatException) {
            return ""
        }
    }

    private fun removeSpecialCharacters(value: String): String {
        val resultString = StringBuilder("")
        for (i in value) {
            if (i.isDigit()) {
                resultString.append(i)
            }
        }
        return resultString.toString()
    }
}