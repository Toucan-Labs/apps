package com.toucanpay.ui.components.account

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEmptyEvent
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.account.mvi.AccountResult
import com.toucanpay.ui.components.account.mvi.AccountResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountViewState(
    val inProgress: Boolean,
    val username: String?,
    val referralCode: String?,
    val logOutSuccess: ViewStateEmptyEvent?,
    val error: ViewStateEvent<String>?
): MviViewState<AccountResult> {

    companion object {
        fun default() = AccountViewState(false, null, null, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: AccountResult): AccountViewState = when (result) {
        is InFlight -> result.reduce()
        is Error -> result.reduce()
        is Success -> result.reduce()
        is LogOutSuccess -> result.reduce()
    }

    private fun InFlight.reduce() = this@AccountViewState.copy(
        inProgress = true,
        error = null,
        logOutSuccess = null
    )

    private fun Error.reduce() = this@AccountViewState.copy(
        inProgress = false,
        error = ViewStateEvent(errorMessage),
        logOutSuccess = null
    )

    private fun Success.reduce() = this@AccountViewState.copy(
        inProgress = false,
        username = username,
        referralCode = referralCode,
        logOutSuccess = null,
        error = null
    )

    private fun LogOutSuccess.reduce() = this@AccountViewState.copy(
        inProgress = false,
        logOutSuccess = ViewStateEmptyEvent(),
        error = null
    )
}