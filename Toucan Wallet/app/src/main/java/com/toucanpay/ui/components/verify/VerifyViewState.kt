package com.toucanpay.ui.components.verify

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEmptyEvent
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.verify.mvi.VerifyResult
import com.toucanpay.ui.components.verify.mvi.VerifyResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyViewState(
    val username: String?,
    val inProgress: Boolean,
    val userVerified: ViewStateEmptyEvent?,
    val switchUser: ViewStateEmptyEvent?,
    val errorMessage: ViewStateEvent<String>?
): MviViewState<VerifyResult> {

    companion object {
        fun default() = VerifyViewState(null, false, null, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: VerifyResult): VerifyViewState = when (result) {
        is InFlight -> result.reduce()
        is Success -> result.reduce()
        is MoveToLogInUser -> result.reduce()
        is Error -> result.reduce()
    }

    private fun InFlight.reduce() = this@VerifyViewState.copy(
        inProgress = true,
        userVerified = null,
        switchUser = null,
        errorMessage = null
    )

    private fun MoveToLogInUser.reduce() = this@VerifyViewState.copy(
        inProgress = false,
        userVerified = null,
        switchUser = ViewStateEmptyEvent(),
        errorMessage = null
    )

    private fun Success.reduce() = this@VerifyViewState.copy(
        inProgress = false,
        userVerified = ViewStateEmptyEvent(),
        switchUser = null,
        errorMessage = null
    )

    private fun Error.reduce() = this@VerifyViewState.copy(
        inProgress = false,
        userVerified = null,
        switchUser = null,
        errorMessage = ViewStateEvent(errorMessage)
    )
}