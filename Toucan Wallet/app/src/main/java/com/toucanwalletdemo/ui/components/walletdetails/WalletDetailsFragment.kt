package com.toucanwalletdemo.ui.components.walletdetails

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Balance
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsAction
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsResult
import com.toucanwalletdemo.ui.components.walletdetails.transactions.TransactionsAdapter
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import com.toucanwalletdemo.utils.utils.cycleTextViewExpansion
import com.toucanwalletdemo.utils.utils.getUriString
import kotlinx.android.synthetic.main.appbar_wallet_details.*
import kotlinx.android.synthetic.main.fragment_wallet_details.*
import java.math.BigDecimal

class WalletDetailsFragment: MviBaseFragment<WalletDetailsAction, WalletDetailsResult, WalletDetailsViewState, WalletDetailsViewModel>(
    WalletDetailsViewModel::class.java
) {

    private val args by navArgs<WalletDetailsFragmentArgs>()
    private val transactionAdapter = TransactionsAdapter()
    private var showingFirst = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_wallet_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initToolbar(args.balance)
        initViews(args.balance)

        viewModel.onGetTransactions(args.balance.tokenSymbol)

        marketingSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onMarketingAccept(isChecked)
        }

        descriptionText.setOnClickListener {
            expandableDescription()
        }

        collapseText.setOnClickListener {
            expandableDescription()
        }
    }

    private fun expandableDescription() {
        showingFirst = if (showingFirst) {
            collapseText.setImageResource(R.drawable.ic_collapse_text)
            false
        } else {
            collapseText.setImageResource(R.drawable.ic_expand_text)
            true
        }
        cycleTextViewExpansion(descriptionText)
    }

    private fun initRecyclerView() {
        transactionsRecyclerView.adapter = transactionAdapter
        transactionsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
    }

    private fun initToolbar(balance: Balance) = with(balance) {
        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)
            it.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }

        collapsingToolbar?.let {
            it.title = tokenName
            it.setContentScrimColor(Color.parseColor(colors[0]))
            it.setExpandedTitleColor(ContextCompat.getColor(requireContext(), R.color.white))
            it.setCollapsedTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun initViews(balance: Balance) = with(balance) {
        backgroundImage.setColorFilter(Color.parseColor(colors[0]), PorterDuff.Mode.SCREEN)
        tokenSymbolText.text = tokenSymbol
    }

    override fun render(viewState: WalletDetailsViewState) = with(viewState) {
        transactionsRecyclerView.isInvisible = transactions.isNullOrEmpty()

        marketingSwitch.isChecked = isChecked

        token?.let {
            with(it) {
                transactionViews.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                emptyTransactionListText.isVisible = transactions.isNullOrEmpty()

                issuerText.text = issuer
                descriptionText.text = Html.fromHtml(description)
                collapseText.isGone = descriptionText.lineCount <= 3
                webLinkText.text = url

                webLinkText.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, getUriString(url)).apply {
                        startActivity(this)
                    }
                }
            }
        }

        balance?.let {
            amountText.isInvisible = it == "0"
            amountText.text = BigDecimal(it).toBigDecimalFormat()
        }

        if (!transactions.isNullOrEmpty()) {
            transactionAdapter.setTransactions(transactions)
        }
    }
}