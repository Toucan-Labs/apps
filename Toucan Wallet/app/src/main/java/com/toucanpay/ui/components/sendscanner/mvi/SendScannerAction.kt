package com.toucanpay.ui.components.sendscanner.mvi

import com.toucanpay.data.models.SendTokenData
import com.toucanpay.ui.base.mvi.MviAction

sealed class SendScannerAction: MviAction {

    data class SendTokensAction(val data: SendTokenData): SendScannerAction()
}