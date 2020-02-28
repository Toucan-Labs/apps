package com.toucanpay.ui.components.claimbonus.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class ClaimBonusAction: MviAction {

//    object GetRewardAction: ClaimBonusAction()

    object ClaimRewardAction: ClaimBonusAction()
}