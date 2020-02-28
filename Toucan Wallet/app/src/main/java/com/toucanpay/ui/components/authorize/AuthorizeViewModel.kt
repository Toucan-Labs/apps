package com.toucanpay.ui.components.authorize

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.authorize.mvi.AuthorizeAction
import com.toucanpay.ui.components.authorize.mvi.AuthorizeActionProcessor
import com.toucanpay.ui.components.authorize.mvi.AuthorizeResult

class AuthorizeViewModel: MviViewModel<AuthorizeAction, AuthorizeResult, AuthorizeViewState>(
    AuthorizeActionProcessor(),
    AuthorizeViewState.default()
) {

    override fun initialAction(): AuthorizeAction? = null

    fun onAccountInfo() = accept(AuthorizeAction.LoadAccountInfoAction)
}