package com.toucanpay.ui.components.tokenpicker

import com.toucanpay.data.models.Token
import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.tokenpicker.mvi.TokenPickerResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenPickerViewState(
    val inProgress: Boolean,
    val tokens: List<Token>?,
    val error: ViewStateEvent<String>?
): MviViewState<TokenPickerResult> {

    companion object {
        fun default() = TokenPickerViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: TokenPickerResult): TokenPickerViewState = when (result) {
        is TokenPickerResult.InFlight -> result.reduce()
        is TokenPickerResult.Success -> result.reduce()
        is TokenPickerResult.Error -> result.reduce()
    }

    private fun TokenPickerResult.InFlight.reduce() = this@TokenPickerViewState.copy(
        inProgress = true,
        tokens = null,
        error = null
    )

    private fun TokenPickerResult.Error.reduce() = this@TokenPickerViewState.copy(
        inProgress = false,
        tokens = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun TokenPickerResult.Success.reduce() = this@TokenPickerViewState.copy(
        inProgress = false,
        tokens = tokens,
        error = null
    )
}