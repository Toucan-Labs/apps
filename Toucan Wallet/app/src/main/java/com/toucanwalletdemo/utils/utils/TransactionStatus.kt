package com.toucanwalletdemo.utils.utils

enum class Status(val status: String) {
    COMPLETED("completed"),
    DEPOSITED("deposited"),
    PENDING("pending"),
    REJECTED("rejected"),
    CANCELLED("cancelled"),
    EXPIRED("expired")
}