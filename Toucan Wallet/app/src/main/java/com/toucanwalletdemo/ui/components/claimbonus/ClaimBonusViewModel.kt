package com.toucanwalletdemo.ui.components.claimbonus

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusAction
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusActionProcessor
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusResult
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