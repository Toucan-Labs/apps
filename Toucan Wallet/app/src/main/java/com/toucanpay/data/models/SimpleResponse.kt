package com.toucanpay.data.models

import java.io.Serializable

data class SimpleResponse(
    val message: String?,
    val error: String?
): Serializable