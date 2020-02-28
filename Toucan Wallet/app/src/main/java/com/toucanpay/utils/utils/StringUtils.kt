package com.toucanpay.utils.utils

import android.net.Uri

fun getUriString(url: String): Uri =
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        Uri.parse("http://$url")
    } else {
        Uri.parse(url)
    }