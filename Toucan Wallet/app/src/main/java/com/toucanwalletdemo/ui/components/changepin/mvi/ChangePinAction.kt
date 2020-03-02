package com.toucanwalletdemo.ui.components.changepin.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction
import com.toucanwalletdemo.ui.model.UserChangePinData

sealed class ChangePinAction: MviAction {

    data class ChangeUserPinAction(val data: UserChangePinData): ChangePinAction()
}