package com.toucanwalletdemo.ui.components.signup

import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEmptyEvent
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpResult
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpViewState(
    val inProgress: Boolean,
    val success: ViewStateEmptyEvent?,
    val errorMessage: ViewStateEvent<String>?
): MviViewState<SignUpResult> {

    companion object {
        fun default() = SignUpViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: SignUpResult): SignUpViewState = when (result) {
        is InFlight -> result.reduce()
        is Success -> result.reduce()
        is Error -> result.reduce()
    }

    private fun InFlight.reduce() = this@SignUpViewState.copy(
        inProgress = true,
        success = null,
        errorMessage = null
    )

    private fun Success.reduce() = this@SignUpViewState.copy(
        inProgress = false,
        success = ViewStateEvent(),
        errorMessage = null
    )

    private fun Error.reduce() = this@SignUpViewState.copy(
        inProgress = false,
        success = null,
        errorMessage = ViewStateEvent(this.errorMessage)
    )
}