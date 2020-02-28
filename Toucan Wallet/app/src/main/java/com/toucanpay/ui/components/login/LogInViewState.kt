package com.toucanpay.ui.components.login

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEmptyEvent
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.login.mvi.LogInResult
import com.toucanpay.ui.components.login.mvi.LogInResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogInViewState(
    val inProgress: Boolean,
    val userLoggedIn: ViewStateEvent<Boolean>?,
    val changePIN: ViewStateEmptyEvent?,
    val errorMessage: ViewStateEvent<String>?
): MviViewState<LogInResult> {

    companion object {
        fun default() = LogInViewState(false, null, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: LogInResult): LogInViewState {
        return when (result) {
            is InFlight -> result.reduce()
            is UserLoggedIn -> result.reduce()
            is PINChangedSuccess -> result.reduce()
            is Error -> result.reduce()
        }
    }

    private fun InFlight.reduce() = this@LogInViewState.copy(
        inProgress = true,
        userLoggedIn = null,
        changePIN = null,
        errorMessage = null
    )

    private fun Error.reduce() = this@LogInViewState.copy(
        inProgress = false,
        userLoggedIn = null,
        changePIN = null,
        errorMessage = ViewStateEvent(errorMessage)
    )

    private fun UserLoggedIn.reduce() = this@LogInViewState.copy(
        inProgress = false,
        userLoggedIn = ViewStateEvent(isRewardClaimed),
        changePIN = null,
        errorMessage = null
    )

    private fun PINChangedSuccess.reduce() = this@LogInViewState.copy(
        inProgress = false,
        userLoggedIn = null,
        changePIN = ViewStateEmptyEvent(),
        errorMessage = null
    )
}