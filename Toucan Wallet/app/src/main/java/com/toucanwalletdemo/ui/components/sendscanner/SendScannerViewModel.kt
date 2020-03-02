package com.toucanwalletdemo.ui.components.sendscanner

import com.toucanwalletdemo.data.models.SendTokenData
import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.sendscanner.mvi.SendScannerAction
import com.toucanwalletdemo.ui.components.sendscanner.mvi.SendScannerActionProcessor
import com.toucanwalletdemo.ui.components.sendscanner.mvi.SendScannerResult
import java.math.BigDecimal

class SendScannerViewModel: MviViewModel<SendScannerAction, SendScannerResult, SendScannerViewState>(
    SendScannerActionProcessor(), SendScannerViewState.default()
) {

    override fun initialAction(): SendScannerAction? = null

    fun onSendTokens(tokenSymbol: String, amount: BigDecimal, toAccountId: String, reference: String) =
        accept(SendScannerAction.SendTokensAction(SendTokenData(tokenSymbol, amount, toAccountId, reference)))
}