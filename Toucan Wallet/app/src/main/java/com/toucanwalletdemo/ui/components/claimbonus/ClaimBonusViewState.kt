package com.toucanwalletdemo.ui.components.claimbonus

import com.toucanwalletdemo.data.models.Reward
import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusResult
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClaimBonusViewState(
    val inProgress: Boolean,
    val claimBonusSuccess: ViewStateEvent<Reward>?,
    val error: ViewStateEvent<String>?
): MviViewState<ClaimBonusResult> {

    companion object {
        fun default() = ClaimBonusViewState(false, null, null)
    }

    override fun isSavable(): Boolean = !inProgress

    override fun reduce(result: ClaimBonusResult): ClaimBonusViewState {
        return when (result) {
            is InFlight -> result.reduce()
            is ClaimRewardSuccess -> result.reduce()
            is Error -> result.reduce()
        }
    }

    private fun InFlight.reduce() = this@ClaimBonusViewState.copy(
        inProgress = true,
        claimBonusSuccess = null,
        error = null
    )

    private fun ClaimRewardSuccess.reduce() = this@ClaimBonusViewState.copy(
        inProgress = false,
        claimBonusSuccess = ViewStateEvent(reward),
        error = null
    )

    private fun Error.reduce() = this@ClaimBonusViewState.copy(
        inProgress = false,
        claimBonusSuccess = null,
        error = ViewStateEvent(errorMessage)
    )
}