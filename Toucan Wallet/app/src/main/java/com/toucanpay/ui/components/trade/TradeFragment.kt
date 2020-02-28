package com.toucanpay.ui.components.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.trade.mvi.TradeAction
import com.toucanpay.ui.components.trade.mvi.TradeResult
import com.toucanpay.ui.model.TradeInputData
import com.toucanpay.utils.utils.hideSoftInput
import com.toucanpay.utils.utils.vibrate
import kotlinx.android.synthetic.main.fragment_trade.*
import kotlinx.android.synthetic.main.view_trade_amount.view.*
import java.math.BigDecimal

class TradeFragment: MviBaseFragment<TradeAction, TradeResult, TradeViewState, TradeViewModel>(
    TradeViewModel::class.java
) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trade, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradeAmountView.tokenContainer.setOnClickListener {
            hideSoftInput(requireContext(), view)
            navController.navigate(R.id.navigateToTokenPickerFragment)
        }

        viewModel.onGetToken(mainViewModel.token)

        backButton.setOnClickListener {
            hideSoftInput(requireContext(), view)
            navController.navigateUp()
        }

        sendButton.setOnClickListener {
            val amount = tradeAmountView.getAmount()
            when {
                amount == null -> Toast.makeText(requireContext(), getString(R.string.trade_error_amount_empty), Toast.LENGTH_SHORT).show()
                usernameInput.text.toString().isEmpty() ->
                    navController.navigate(TradeFragmentDirections.navigateToSendScannerFragment(getTradeInput(amount)))
                else -> sendTokens(amount)
            }
        }

        requestButton.setOnClickListener {
            val amount = tradeAmountView.getAmount()
            if (amount == null) {
                Toast.makeText(requireContext(), getString(R.string.trade_error_amount_empty), Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate(TradeFragmentDirections.navigateToRequestScannerFragment(getTradeInput(amount)))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        with(mainViewModel) {
            if (!token.isBlank()) {
                tradeAmountView.tokenContainer.tokenText.text = token
            }
            if (tradeTokensSuccess) {
                tradeTokensSuccess = false
                clearInputs()
            }
        }
    }

    override fun render(viewState: TradeViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            sendButton.isInvisible = inProgress
            requestButton.isInvisible = inProgress
            tradeAmountView.tokenContainer.isEnabled = !inProgress
            tradeAmountView.amountInput.isEnabled = !inProgress
            usernameInput.isEnabled = !inProgress
            referenceInput.isEnabled = !inProgress

            token?.let {
                if (mainViewModel.token.isBlank()) {
                    tradeAmountView.tokenContainer.tokenText.text = it
                }
            }

            success?.consume {
                vibrate(requireActivity())
                it?.let {
                    navController.navigate(TradeFragmentDirections.navigateToTradeTokensSuccess(it, true))
                }
                clearInputs()
            }

            error?.consume {
                it?.let {
                    navController.navigate(TradeFragmentDirections.navigateToTradeTokensError(it))
                }
            }

            tokenError?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendTokens(amount: BigDecimal) = viewModel.onSendTokens(
        tradeAmountView.getToken(),
        amount,
        usernameInput.text.toString(),
        referenceInput.text.toString()
    )

    private fun getTradeInput(amount: BigDecimal) =
        TradeInputData(
            referenceInput.text.toString(),
            tradeAmountView.getToken(),
            amount,
            referenceInput.text.toString()
        )

    private fun clearInputs() {
        usernameInput.text.clear()
        referenceInput.text.clear()
        tradeAmountView.amountInput.setText("")
    }

    override fun hasBottomBar() = false
}