package com.toucanwalletdemo.ui.components.authorize.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult
import com.toucanwalletdemo.ui.model.QrCodeSignatureData

sealed class AuthorizeResult: MviResult {

    object InFlight: AuthorizeResult()

    data class Success(val qrCodeSignatureData: QrCodeSignatureData): AuthorizeResult()

    data class Error(val errorMessage: String): AuthorizeResult()
}