package com.toucanwalletdemo.ui.components.requestscanner

import com.toucanwalletdemo.data.models.RequestTokenData
import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerAction
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerActionProcessor
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerResult
import java.math.BigDecimal

class RequestScannerViewModel: MviViewModel<RequestScannerAction, RequestScannerResult, RequestScannerViewState>(
    RequestScannerActionProcessor(), RequestScannerViewState.default()
) {

    override fun initialAction(): RequestScannerAction? = null

    fun onRequestTokens(signature: String, random: String, username: String, amount: BigDecimal, tokenSymbol: String, reference: String) =
        accept(RequestScannerAction.RequestTokensAction(RequestTokenData(signature, random, username, amount, tokenSymbol, reference)))
}