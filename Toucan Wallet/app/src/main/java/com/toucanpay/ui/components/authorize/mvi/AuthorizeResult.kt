package com.toucanpay.ui.components.authorize.mvi

import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.ui.model.QrCodeSignatureData

sealed class AuthorizeResult: MviResult {

    object InFlight: AuthorizeResult()

    data class Success(val qrCodeSignatureData: QrCodeSignatureData): AuthorizeResult()

    data class Error(val errorMessage: String): AuthorizeResult()
}