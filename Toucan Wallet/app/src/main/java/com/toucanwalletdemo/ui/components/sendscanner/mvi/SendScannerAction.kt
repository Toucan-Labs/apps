package com.toucanwalletdemo.ui.components.sendscanner.mvi

import com.toucanwalletdemo.data.models.SendTokenData
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class SendScannerAction: MviAction {

    data class SendTokensAction(val data: SendTokenData): SendScannerAction()
}