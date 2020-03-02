package com.toucanwalletdemo.ui.components.wallets.balances

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Balance
import com.toucanwalletdemo.utils.extensions.inflateItem
import com.toucanwalletdemo.utils.extensions.setImageViewBackground
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import com.toucanwalletdemo.utils.utils.dpToPx
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_wallets_balance.*
import java.math.BigDecimal

class BalancesAdapter: RecyclerView.Adapter<BalanceViewHolder>() {

    private var balances = emptyList<Balance>()
    var onItemClickListener: ((Balance) -> Unit)? = null

    fun setBalance(newBalance: List<Balance>) {
        balances = newBalance
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BalanceViewHolder(parent)

    override fun getItemCount() = balances.size

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) =
        holder.bind(balances[position], ::onItemClicked)

    private fun onItemClicked(balance: Balance) {
        onItemClickListener?.invoke(balance)
    }
}

class BalanceViewHolder(
    var parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.view_wallets_balance)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(balance: Balance, onItemClickListener: (Balance) -> Unit) = with(balance) {
        itemView.setOnClickListener { onItemClickListener(this) }

        amountText.isInvisible = this.balance == "0"
        amountText.text = BigDecimal(this.balance).toBigDecimalFormat()
        tokenNameText.text = tokenName
        tokenSymbolText.text = tokenSymbol

        backgroundImage.setImageViewBackground(Color.parseColor(colors[0]), dpToPx(25).toFloat())
    }
}