package com.toucanpay.ui.components.requestscanner.mvi

import com.toucanpay.data.models.RequestTokenData
import com.toucanpay.ui.base.mvi.MviAction

sealed class RequestScannerAction: MviAction {

    data class RequestTokensAction(val data: RequestTokenData): RequestScannerAction()
}