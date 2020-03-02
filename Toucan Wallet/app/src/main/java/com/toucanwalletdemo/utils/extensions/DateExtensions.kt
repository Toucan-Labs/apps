package com.toucanwalletdemo.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_TRANSACTION = "dd.MM.yy hh:mm a"
const val DATE_FORMAT_HOUR = "HH:mm"
const val DATE_FORMAT_DAY_HEADER = "EEEE, dd.MM.yy"

fun Date?.toFormattedString(format: String = DATE_FORMAT_TRANSACTION): String? {
    if (this == null) {
        return null
    }
    return try {
        SimpleDateFormat(format, Locale.ENGLISH).format(this)
    } catch (e: Exception) {
        null
    }
}

fun Date?.toFormattedDayString(format: String = DATE_FORMAT_DAY_HEADER): String? {
    if (this == null) {
        return null
    }
    return try {
        SimpleDateFormat(format, Locale.ENGLISH).format(this)
    } catch (e: Exception) {
        null
    }
}

fun Date?.toFormattedHourString(format: String = DATE_FORMAT_HOUR): String? {
    if (this == null) {
        return null
    }
    return try {
        SimpleDateFormat(format, Locale.ENGLISH).format(this)
    } catch (e: Exception) {
        null
    }
}