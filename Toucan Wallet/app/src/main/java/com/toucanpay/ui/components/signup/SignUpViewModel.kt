package com.toucanpay.ui.components.signup

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.signup.mvi.SignUpAction
import com.toucanpay.ui.components.signup.mvi.SignUpActionProcessor
import com.toucanpay.ui.components.signup.mvi.SignUpResult
import com.toucanpay.ui.model.UserRegistrationData

class SignUpViewModel: MviViewModel<SignUpAction, SignUpResult, SignUpViewState>(
    SignUpActionProcessor(),
    SignUpViewState.default()
) {

    override fun initialAction(): SignUpAction? = null

    fun onRegister(registrationData: UserRegistrationData) =
        accept(SignUpAction.RegisterAction(registrationData))
}