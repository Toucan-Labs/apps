package com.toucanpay.ui.components.changepin

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.changepin.mvi.ChangePinAction
import com.toucanpay.ui.components.changepin.mvi.ChangePinActionProcessor
import com.toucanpay.ui.components.changepin.mvi.ChangePinResult
import com.toucanpay.ui.model.UserChangePinData

class ChangePinViewModel: MviViewModel<ChangePinAction, ChangePinResult, ChangePinViewState>(
    ChangePinActionProcessor(),
    ChangePinViewState.default()
) {

    override fun initialAction(): ChangePinAction? = null

    fun onChangePin(userChangePinData: UserChangePinData) {
        accept(ChangePinAction.ChangeUserPinAction(userChangePinData))
    }
}