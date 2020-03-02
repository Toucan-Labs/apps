package com.toucanwalletdemo.ui.components.swaps

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.swaps.adapter.TradesAdapter
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsAction
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsResult
import com.toucanwalletdemo.utils.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_swaps.*

class SwapsFragment: MviBaseFragment<SwapsAction, SwapsResult, SwapsViewState, SwapsViewModel>(
    SwapsViewModel::class.java
) {

    private val tradesAdapter = TradesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_swaps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTradesRecyclerView()

        acceptButton.setOnClickListener {
            val position = getAdapterPosition()
            when {
                position < 0 -> invalidAdapterPositionToast()
                else -> viewModel.onFulfilTrade(position)
            }
        }

        rejectButton.setOnClickListener {
            val position = getAdapterPosition()
            when {
                position < 0 -> invalidAdapterPositionToast()
                else -> viewModel.onRejectTrade(position)
            }
        }
    }

    private fun initTradesRecyclerView() = tradesRecyclerView.apply {
        adapter = tradesAdapter
        PagerSnapHelper().attachToRecyclerView(this)
        addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))
    }

    override fun render(viewState: SwapsViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            progressBarTrades.isVisible = inProgressTrades
            tradesRecyclerView.isInvisible = trades.isNullOrEmpty()
            tradeButtons.isInvisible = trades.isNullOrEmpty() || inProgressTrades
            emptyListText.isVisible = trades.isNullOrEmpty() && !inProgress

            if (!trades.isNullOrEmpty()) {
                tradesAdapter.setTrades(trades)
            }

            if (!tokens.isNullOrEmpty()) {
                tradesAdapter.setTokens(tokens)
            }

            tradeRequestSuccess?.consume {
                it?.let {
                    navController.navigate(SwapsFragmentDirections.navigateToSwapRequestSuccessDialog(it))
                }
            }

            tradeRequestError?.consume {
                it?.let {
                    navController.navigate(SwapsFragmentDirections.navigateToSwapRequestErrorDialog(it))
                }
            }
        }
    }

    private fun invalidAdapterPositionToast() =
        Toast.makeText(requireContext(), getString(R.string.swaps_invalid_adapter_position), Toast.LENGTH_SHORT).apply {
            view.findViewById<TextView>(android.R.id.message).gravity = Gravity.CENTER
            show()
        }

    private fun getAdapterPosition() =
        (tradesRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
}