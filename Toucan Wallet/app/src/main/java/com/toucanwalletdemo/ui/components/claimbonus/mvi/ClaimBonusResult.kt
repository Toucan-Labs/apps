package com.toucanwalletdemo.ui.components.claimbonus.mvi

import com.toucanwalletdemo.data.models.Reward
import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class ClaimBonusResult: MviResult {

    object InFlight: ClaimBonusResult()

    data class ClaimRewardSuccess(val reward: Reward): ClaimBonusResult()

    data class Error(val errorMessage: String): ClaimBonusResult()
}