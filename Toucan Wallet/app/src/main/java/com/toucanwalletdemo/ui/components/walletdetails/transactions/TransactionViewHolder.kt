package com.toucanwalletdemo.ui.components.walletdetails.transactions

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Transaction
import com.toucanwalletdemo.utils.extensions.inflateItem
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import com.toucanwalletdemo.utils.extensions.toFormattedString
import com.toucanwalletdemo.utils.utils.Status
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_transaction.*
import java.math.BigDecimal
import java.util.*

class TransactionViewHolder(
    var parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.item_transaction)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(transaction: Transaction) = with(transaction) {

        val isIncome = toAccountId == accountUsername
        val color = getTransactionColor(status, isIncome)

        setUsername(fromAccountId, toAccountId, status, hash, accountUsername)
        setIcon(color, isIncome)
        setExpires(expirySeconds, status)
        setAmount(BigDecimal(amount), color, isIncome)
        setTimestamp(createdAt)
        setReferenceText(reference, status)
    }

    private fun setUsername(fromAccountId: String, toAccountId: String, status: String, hash: String, username: String?) {
        usernameText.text = when {
            status == Status.DEPOSITED.status -> hash
            fromAccountId == username -> toAccountId
            else -> fromAccountId
        }
    }

    private fun setIcon(color: Int, isIncome: Boolean) {
        val drawable = VectorDrawableCompat.create(
            parent.resources,
            if (isIncome) R.drawable.ic_arrow_bottom_left else R.drawable.ic_arrow_top_right,
            null
        )
        transactionIcon.setImageDrawable(drawable)
        transactionIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    private fun setExpires(expires: Long?, status: String) {
        expiresText.isGone = expires != null && status != Status.PENDING.status
        expiresText.text = if (expires != null) {
            val expiresMin = (expires / 60L).toInt()
            "Expires: $expiresMin min"
        } else {
            ""
        }
    }

    private fun setReferenceText(reference: String, status: String) {
        referenceText.isGone = reference.isEmpty() && status != Status.DEPOSITED.status
        referenceText.text = when (status) {
            Status.DEPOSITED.status -> Status.DEPOSITED.status
            else -> reference
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAmount(amount: BigDecimal, color: Int, isIncome: Boolean) {
        val sign = if (isIncome) "+" else "-"
        amountText.text = "$sign ${amount.toBigDecimalFormat()}"
        amountText.setTextColor(color)
    }

    private fun setTimestamp(timestamp: Long) {
        timestampText.text = Date(timestamp * 1000).toFormattedString()
    }

    private fun getTransactionColor(status: String, isIncome: Boolean) =
        ContextCompat.getColor(
            parent.context, when (status) {
                Status.COMPLETED.status -> if (isIncome) R.color.blue else R.color.colorPrimary
                Status.DEPOSITED.status -> R.color.blue
                else -> R.color.grayArrow
            }
        )
}