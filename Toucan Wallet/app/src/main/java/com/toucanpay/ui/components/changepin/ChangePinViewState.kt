package com.toucanpay.ui.components.changepin

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEmptyEvent
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.changepin.mvi.ChangePinResult
import com.toucanpay.ui.components.changepin.mvi.ChangePinResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePinViewState(
    val inProgress: Boolean,
    val success: ViewStateEmptyEvent?,
    val errorMessage: ViewStateEvent<String>?
): MviViewState<ChangePinResult> {

    companion object {
        fun default() = ChangePinViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: ChangePinResult): ChangePinViewState {
        return when (result) {
            is InFlight -> result.reduce()
            is Success -> result.reduce()
            is Error -> result.reduce()
        }
    }

    private fun InFlight.reduce() = this@ChangePinViewState.copy(
        inProgress = true,
        success = null,
        errorMessage = null
    )

    private fun Success.reduce() = this@ChangePinViewState.copy(
        inProgress = false,
        success = ViewStateEmptyEvent(),
        errorMessage = null
    )

    private fun Error.reduce() = this@ChangePinViewState.copy(
        inProgress = false,
        success = null,
        errorMessage = ViewStateEvent(this.errorMessage)
    )
}