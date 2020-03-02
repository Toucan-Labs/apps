package com.toucanwalletdemo.ui.components.signup

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpAction
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpActionProcessor
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpResult
import com.toucanwalletdemo.ui.model.UserRegistrationData

class SignUpViewModel: MviViewModel<SignUpAction, SignUpResult, SignUpViewState>(
    SignUpActionProcessor(),
    SignUpViewState.default()
) {

    override fun initialAction(): SignUpAction? = null

    fun onRegister(registrationData: UserRegistrationData) =
        accept(SignUpAction.RegisterAction(registrationData))
}