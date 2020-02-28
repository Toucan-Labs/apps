package com.toucanpay.ui.components.tokenpicker.mvi

import com.toucanpay.data.models.Token
import com.toucanpay.ui.base.mvi.MviResult

sealed class TokenPickerResult: MviResult {

    object InFlight: TokenPickerResult()

    data class Success(val tokens: List<Token>?): TokenPickerResult()

    data class Error(val errorMessage: String): TokenPickerResult()
}