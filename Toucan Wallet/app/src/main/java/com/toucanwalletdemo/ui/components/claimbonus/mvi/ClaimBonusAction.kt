package com.toucanwalletdemo.ui.components.claimbonus.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class ClaimBonusAction: MviAction {

//    object GetRewardAction: ClaimBonusAction()

    object ClaimRewardAction: ClaimBonusAction()
}