package com.toucanpay.ui.components.changepin.mvi

import com.toucanpay.ui.base.mvi.MviAction
import com.toucanpay.ui.model.UserChangePinData

sealed class ChangePinAction: MviAction {

    data class ChangeUserPinAction(val data: UserChangePinData): ChangePinAction()
}