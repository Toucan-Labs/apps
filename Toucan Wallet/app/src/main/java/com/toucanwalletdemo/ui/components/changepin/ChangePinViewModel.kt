package com.toucanwalletdemo.ui.components.changepin

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.changepin.mvi.ChangePinAction
import com.toucanwalletdemo.ui.components.changepin.mvi.ChangePinActionProcessor
import com.toucanwalletdemo.ui.components.changepin.mvi.ChangePinResult
import com.toucanwalletdemo.ui.model.UserChangePinData

class ChangePinViewModel: MviViewModel<ChangePinAction, ChangePinResult, ChangePinViewState>(
    ChangePinActionProcessor(),
    ChangePinViewState.default()
) {

    override fun initialAction(): ChangePinAction? = null

    fun onChangePin(userChangePinData: UserChangePinData) {
        accept(ChangePinAction.ChangeUserPinAction(userChangePinData))
    }
}