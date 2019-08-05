package com.example.anothertimdatxe.util

import java.text.SimpleDateFormat

object DateUtil {
    val DATE_FORMAT_1 = "yyyy-MM-dd"
    val DATE_FORMAT_2 = "HH:mm:ss"
    val DATE_FORMAT_3 = "h:mm a"
    val DATE_FORMAT_4 = "dd/MM/yyyy"
    val DATE_FORMAT_5 = "hh:mmaa dd/MM/yyyy"
    val DATE_FORMAT_6 = "MM/dd/yyyy"
    val DATE_FORMAT_7 = "HH:mm"
    val DATE_FORMAT_8 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    val DATE_FORMAT_9 = "$DATE_FORMAT_1 $DATE_FORMAT_2"
    val DATE_FORMAT_10 = "hh:mmaa"
    val DATE_FORMAT_11 = "hh:mm dd/MM/yyyy"
    val DATE_FORMAT_12 = "ddHHmmss"
    val DATE_FORMAT_13 = "yyyy-MM-dd HH:mm:ss"
    val DATE_FORMAT_14 = "EEEE"
    val DATE_FORMAT_15 = "HH:mm"
    val DATE_FORMAT_16 = "dd"
    val DATE_FORMAT_17 = "MM"
    val DATE_FORMAT_18 = "yyyy"
    val DATE_FORMAT_19 = "HH:mm dd/MM/yyyy"
    val DATE_FORMAT_20 = "dd-MM-yyyy HH:mm"
    val DATE_FORMAT_22 = "yyyy-MM-dd HH:mm"
    val DATE_FORMAT_21 = "dd-MM-yyyy"
    val DATE_FORMAT_23 = "dd/MM/yyyy"
    val DATE_FORMAT_24 = "h:mm a - dd/MM/yyyy"

    val TIME_0H = "00:00:00"
    val TIME_7H = "07:00:00"
    val TIME_START = 0
    val TIME_END_MINUTE = 59
    val TIME_END_HOUR = 23

    fun formatDate(day: String, initFormat: String, resultFormat: String): String {
        val initFormater = SimpleDateFormat(initFormat)
        val resultFormater = SimpleDateFormat(resultFormat)
        return resultFormater.format(initFormater.parse(day))
    }
}