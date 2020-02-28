package com.toucanpay.ui.components.claimbonus.mvi

import com.toucanpay.data.models.Reward
import com.toucanpay.ui.base.mvi.MviResult

sealed class ClaimBonusResult: MviResult {

    object InFlight: ClaimBonusResult()

    data class ClaimRewardSuccess(val reward: Reward): ClaimBonusResult()

    data class Error(val errorMessage: String): ClaimBonusResult()
}