package com.toucanwalletdemo.ui.components.requestscanner.mvi

import com.toucanwalletdemo.data.models.RequestTokenData
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class RequestScannerAction: MviAction {

    data class RequestTokensAction(val data: RequestTokenData): RequestScannerAction()
}