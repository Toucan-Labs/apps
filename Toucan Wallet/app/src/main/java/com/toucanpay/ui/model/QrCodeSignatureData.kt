package com.toucanpay.ui.model

import java.io.Serializable

data class QrCodeSignatureData(
    val username: String,
    val signature: String,
    val random: String
): Serializable