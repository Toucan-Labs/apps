package com.toucanwalletdemo.utils.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun BigDecimal.toBigDecimalFormat(): String =
    DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.ENGLISH)).format(this)

fun BigDecimal.toBigDecimalTradeFormat(): String =
    DecimalFormat("#0.00", DecimalFormatSymbols(Locale.ENGLISH)).format(this)