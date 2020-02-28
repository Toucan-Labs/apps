package com.toucanpay.ui.components.signup.mvi

import com.toucanpay.ui.base.mvi.MviAction
import com.toucanpay.ui.model.UserRegistrationData

sealed class SignUpAction: MviAction {

    data class RegisterAction(val data: UserRegistrationData): SignUpAction()
}