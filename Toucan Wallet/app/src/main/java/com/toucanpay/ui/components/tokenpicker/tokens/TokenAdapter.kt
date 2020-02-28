package com.toucanpay.ui.components.tokenpicker.tokens

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toucanpay.R
import com.toucanpay.data.models.Token
import com.toucanpay.utils.extensions.inflateItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_token.*

class TokenAdapter: RecyclerView.Adapter<TokenViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    private var tokens = emptyList<Token>()

    fun setTokens(newTokens: List<Token>) {
        tokens = newTokens
        notifyDataSetChanged()
    }

    fun filteredList(filteredList: List<Token>) {
        tokens = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TokenViewHolder(parent)

    override fun getItemCount() = tokens.size

    override fun onBindViewHolder(holder: TokenViewHolder, position: Int) =
        holder.bind(tokens[position], ::onItemClicked)


    private fun onItemClicked(token: String) {
        onItemClickListener?.invoke(token)
    }
}

class TokenViewHolder(
    parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.item_token)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(token: Token, onItemClickListener: (String) -> Unit) = with(token) {
        itemView.setOnClickListener { onItemClickListener("$tokenSymbol - $tokenName") }

        tokenSymbolText.text = tokenSymbol
        tokenNameText.text = tokenName
    }
}