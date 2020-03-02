package com.toucanwalletdemo.ui.components.authorize

import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeResult
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeResult.*
import com.toucanwalletdemo.ui.model.QrCodeSignatureData
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