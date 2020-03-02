package com.toucanwalletdemo.ui.components.walletdetails.transactions

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toucanwalletdemo.data.models.Transaction

class TransactionsAdapter: RecyclerView.Adapter<TransactionViewHolder>() {

    private var transactions = emptyList<Transaction>()

    fun setTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionViewHolder(parent)

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
        holder.bind(transactions[position])
}