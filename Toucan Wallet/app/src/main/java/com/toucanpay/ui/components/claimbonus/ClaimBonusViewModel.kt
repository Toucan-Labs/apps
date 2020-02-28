package com.toucanpay.ui.components.claimbonus

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusAction
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusActionProcessor
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusResult
import org.koin.standalone.KoinComponent

class ClaimBonusViewModel: MviViewModel<ClaimBonusAction, ClaimBonusResult, ClaimBonusViewState>(
    ClaimBonusActionProcessor(),
    ClaimBonusViewState.default()
), KoinComponent {

    override fun initialAction(): ClaimBonusAction? = null

    fun onClaimBonus() {
        accept(ClaimBonusAction.ClaimRewardAction)
    }
}