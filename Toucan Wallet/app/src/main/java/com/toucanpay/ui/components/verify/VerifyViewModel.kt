package com.toucanpay.ui.components.verify

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.verify.mvi.VerifyAction
import com.toucanpay.ui.components.verify.mvi.VerifyActionProcessor
import com.toucanpay.ui.components.verify.mvi.VerifyResult

class VerifyViewModel: MviViewModel<VerifyAction, VerifyResult, VerifyViewState>(
    VerifyActionProcessor(),
    VerifyViewState.default()
) {
    override fun initialAction(): Nothing? = null

    fun onVerify(code: String) = accept(VerifyAction.VerifyUserAction(code))

    fun onSwitchUser() = accept(VerifyAction.SwitchUserAction)
}