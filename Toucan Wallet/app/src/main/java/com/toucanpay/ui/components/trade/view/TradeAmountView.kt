package com.toucanpay.ui.components.trade.view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.toucanpay.R
import com.toucanpay.utils.extensions.toBigDecimalTradeFormat
import com.toucanpay.utils.utils.DecimalDigitsInputFilter
import kotlinx.android.synthetic.main.view_trade_amount.view.*
import java.math.BigDecimal

class TradeAmountView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_trade_amount, this, true)
        amountInput.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(15, 2))

        amountInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                amountInput.setText(getAmount()?.toBigDecimalTradeFormat() ?: "")
            }
        }
    }

    fun getAmount(): BigDecimal? = amountInput.text.toString().toBigDecimal()

    fun getToken(): String = tokenText.text.toString().split(" ").map { it.trim() }[0]

    private fun String.toBigDecimal(): BigDecimal? = try {
        BigDecimal(this)
    } catch (t: Throwable) {
        null
    }
}