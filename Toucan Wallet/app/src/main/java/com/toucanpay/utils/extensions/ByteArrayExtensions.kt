package com.toucanpay.utils.extensions

import android.util.Base64

fun ByteArray.toBase64String(flags: Int = Base64.DEFAULT): String = Base64.encodeToString(this, flags)
