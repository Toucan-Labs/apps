package com.toucanwalletdemo.ui.components.tokenpicker.mvi

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class TokenPickerResult: MviResult {

    object InFlight: TokenPickerResult()

    data class Success(val tokens: List<Token>?): TokenPickerResult()

    data class Error(val errorMessage: String): TokenPickerResult()
}