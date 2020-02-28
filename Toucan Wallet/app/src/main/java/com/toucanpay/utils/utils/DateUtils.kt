package com.toucanpay.utils.utils

import java.util.*

fun getTimeOfDay(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 1..11 -> "Morning"
    in 12..16 -> "Afternoon"
    else -> "Evening"
}