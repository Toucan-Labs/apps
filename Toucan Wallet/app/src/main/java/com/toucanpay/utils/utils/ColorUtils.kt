package com.toucanpay.utils.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.toucanpay.R
import com.toucanpay.data.models.Token
import com.toucanpay.data.models.Transaction

fun getTokenColor(context: Context, transactions: List<Transaction>, tokens: List<Token>?, position: Int) =
    Color.parseColor(
        tokens?.firstOrNull { transactions[position].tokenSymbol == it.tokenSymbol }?.colors?.get(0)
            ?: String.format("#%06x", ContextCompat.getColor(context, R.color.colorPrimary))
    )