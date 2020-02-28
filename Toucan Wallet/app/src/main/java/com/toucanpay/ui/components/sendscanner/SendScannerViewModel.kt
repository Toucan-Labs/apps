package com.toucanpay.ui.components.sendscanner

import com.toucanpay.data.models.SendTokenData
import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.sendscanner.mvi.SendScannerAction
import com.toucanpay.ui.components.sendscanner.mvi.SendScannerActionProcessor
import com.toucanpay.ui.components.sendscanner.mvi.SendScannerResult
import java.math.BigDecimal

class SendScannerViewModel: MviViewModel<SendScannerAction, SendScannerResult, SendScannerViewState>(
    SendScannerActionProcessor(), SendScannerViewState.default()
) {

    override fun initialAction(): SendScannerAction? = null

    fun onSendTokens(tokenSymbol: String, amount: BigDecimal, toAccountId: String, reference: String) =
        accept(SendScannerAction.SendTokensAction(SendTokenData(tokenSymbol, amount, toAccountId, reference)))
}