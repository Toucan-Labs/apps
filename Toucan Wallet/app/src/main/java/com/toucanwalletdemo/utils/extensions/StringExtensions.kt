package com.toucanwalletdemo.utils.extensions

import android.util.Base64

fun String.fromBase64(flags: Int = Base64.DEFAULT): ByteArray = Base64.decode(this, flags)
