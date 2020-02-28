package com.toucanpay.ui.components.signin

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEmptyEvent
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.signin.mvi.SignInResult
import com.toucanpay.ui.components.signin.mvi.SignInResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInViewState(
    val username: String?,
    val inProgress: Boolean,
    val resetPin: ViewStateEmptyEvent?,
    val userLoggedIn: ViewStateEvent<Boolean>?,
    val switchUser: ViewStateEmptyEvent?,
    val errorMessage: ViewStateEvent<String>?
): MviViewState<SignInResult> {

    companion object {
        fun default() = SignInViewState(null, false, null, null, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: SignInResult): SignInViewState = when (result) {
        is InFlight -> result.reduce()
        is Error -> result.reduce()
        is MoveToResetPin -> result.reduce()
        is UserLoaded -> result.reduce()
        is MoveToLogInUser -> result.reduce()
        is UserLoggedIn -> result.reduce()
    }

    private fun InFlight.reduce() = this@SignInViewState.copy(
        inProgress = true,
        userLoggedIn = null,
        resetPin = null,
        switchUser = null,
        errorMessage = null
    )

    private fun Error.reduce() = this@SignInViewState.copy(
        inProgress = false,
        userLoggedIn = null,
        resetPin = null,
        switchUser = null,
        errorMessage = ViewStateEvent(errorMessage)
    )

    private fun UserLoaded.reduce() = this@SignInViewState.copy(
        username = username,
        inProgress = false,
        resetPin = null,
        userLoggedIn = null,
        switchUser = null,
        errorMessage = null
    )

    private fun MoveToResetPin.reduce() = this@SignInViewState.copy(
        inProgress = false,
        resetPin = ViewStateEmptyEvent(),
        userLoggedIn = null,
        switchUser = null,
        errorMessage = null
    )

    private fun MoveToLogInUser.reduce() = this@SignInViewState.copy(
        inProgress = false,
        resetPin = null,
        userLoggedIn = null,
        switchUser = ViewStateEmptyEvent(),
        errorMessage = null
    )

    private fun UserLoggedIn.reduce() = this@SignInViewState.copy(
        inProgress = false,
        userLoggedIn = ViewStateEvent(isRewardClaimed),
        resetPin = null,
        switchUser = null,
        errorMessage = null
    )
}