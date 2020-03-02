package com.toucanwalletdemo.ui.components.swaps.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Trade
import com.toucanwalletdemo.utils.extensions.inflateItem
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import com.toucanwalletdemo.utils.utils.getSwapCardWidth
import com.toucanwalletdemo.utils.utils.getTokenColor
import com.toucanwalletdemo.utils.utils.getTransactionPositions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_trade_item.*
import java.math.BigDecimal

class TradesAdapter: RecyclerView.Adapter<TradesViewHolder>() {

    private var trades = emptyList<Trade>()
    private var tokens = emptyList<Token>()

    fun setTrades(newTrades: List<Trade>) {
        trades = newTrades
        notifyDataSetChanged()
    }

    fun setTokens(newTokens: List<Token>) {
        tokens = newTokens
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TradesViewHolder(parent)

    override fun getItemCount() = trades.size

    override fun onBindViewHolder(holder: TradesViewHolder, position: Int) =
        holder.bind(trades[position], tokens)
}

class TradesViewHolder(
    var parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.view_trade_item)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(trade: Trade, tokens: List<Token>) = with(trade) {

        swapCard.layoutParams.width = getSwapCardWidth(swapCard, parent.context)

        val pos = getTransactionPositions(requests)

        outcomeTokenSymbol.apply {
            text = requests[pos.first].tokenSymbol
            setBackgroundResource(R.drawable.bg_token_symbol_swap)
            DrawableCompat.setTint(this.background, getTokenColor(context, requests, tokens, pos.first))
        }
        outcomeAmountText.text = BigDecimal(requests[pos.first].amount).toBigDecimalFormat()

        incomeTokenSymbol.apply {
            text = requests[pos.second].tokenSymbol
            setBackgroundResource(R.drawable.bg_token_symbol_swap)
            DrawableCompat.setTint(this.background, getTokenColor(context, requests, tokens, pos.second))
        }
        incomeAmountText.text = BigDecimal(requests[pos.second].amount).toBigDecimalFormat()
    }
}