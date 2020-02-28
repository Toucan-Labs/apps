package com.toucanpay.ui.components.authorize

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.authorize.mvi.AuthorizeResult
import com.toucanpay.ui.components.authorize.mvi.AuthorizeResult.*
import com.toucanpay.ui.model.QrCodeSignatureData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorizeViewState(
    val inProgress: Boolean,
    val success: ViewStateEvent<QrCodeSignatureData>?,
    val error: ViewStateEvent<String>?
): MviViewState<AuthorizeResult> {

    companion object {
        fun default() = AuthorizeViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: AuthorizeResult): AuthorizeViewState = when (result) {
        is InFlight -> result.reduce()
        is Error -> result.reduce()
        is Success -> result.reduce()
    }

    private fun InFlight.reduce() = this@AuthorizeViewState.copy(
        inProgress = true,
        success = null,
        error = null
    )

    private fun Error.reduce() = this@AuthorizeViewState.copy(
        inProgress = false,
        success = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun Success.reduce() = this@AuthorizeViewState.copy(
        inProgress = false,
        success = ViewStateEvent(qrCodeSignatureData),
        error = null
    )
}