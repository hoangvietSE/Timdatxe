package com.example.anothertimdatxe.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object NumberUtil {
    private var formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))

    fun formatNumber(value: String): String {
        return formatter.format(value.toInt()) + " VNĐ"
    }
}